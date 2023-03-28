package org.xbib.z3950.common.operations;

import org.xbib.asn1.ASN1GeneralString;
import org.xbib.asn1.ASN1Integer;
import org.xbib.asn1.ASN1Null;
import org.xbib.asn1.ASN1ObjectIdentifier;
import org.xbib.asn1.ASN1OctetString;
import org.xbib.asn1.io.BERReader;
import org.xbib.asn1.io.BERWriter;
import org.xbib.z3950.common.Diagnostics;
import org.xbib.z3950.common.v3.AttributeElement;
import org.xbib.z3950.common.v3.AttributeElementAttributeValue;
import org.xbib.z3950.common.v3.AttributeList;
import org.xbib.z3950.common.v3.AttributeSetId;
import org.xbib.z3950.common.v3.DiagRec;
import org.xbib.z3950.common.v3.InternationalString;
import org.xbib.z3950.common.v3.ReferenceId;
import org.xbib.z3950.common.v3.SortElement;
import org.xbib.z3950.common.v3.SortKey;
import org.xbib.z3950.common.v3.SortKeySortAttributes;
import org.xbib.z3950.common.v3.SortKeySpec;
import org.xbib.z3950.common.v3.SortKeySpecMissingValueAction;
import org.xbib.z3950.common.v3.SortRequest;
import org.xbib.z3950.common.v3.SortResponse;
import org.xbib.z3950.common.v3.Specification;
import org.xbib.z3950.common.v3.SpecificationElementSpec;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SortOperation extends AbstractOperation<SortResponse, SortRequest> {

    private static final Logger logger = Logger.getLogger(SortOperation.class.getName());

    public SortOperation(BERReader reader,
                         BERWriter writer) {
        super(reader, writer);
    }

    public boolean execute(String referenceId,
                           String resultSetName,
                           String sortResultSetName,
                           List<SortParameter> list) throws IOException {
        SortRequest sortRequest = new SortRequest();
        sortRequest.s_inputResultSetNames = new InternationalString[1];
        sortRequest.s_inputResultSetNames[0] = new InternationalString();
        sortRequest.s_inputResultSetNames[0].value = new ASN1GeneralString(resultSetName);
        int i = 0;
        SortKeySpec[] sortKeySpec = new SortKeySpec[list.size()];
        for (SortParameter parameter : list) {
            SortElement sortElement = new SortElement();
            sortElement.c_generic = new SortKey();
            if (parameter.sortField != null) {
                sortElement.c_generic.c_sortfield = new InternationalString();
                sortElement.c_generic.c_sortfield.value = new ASN1GeneralString(parameter.sortField);
            } else if (parameter.attributes != null) {
                sortElement.c_generic.c_sortAttributes = new SortKeySortAttributes();
                sortElement.c_generic.c_sortAttributes.s_id = new AttributeSetId();
                // Z39.50 BIB-1: urn:oid:1.2.840.10003.3.1
                sortElement.c_generic.c_sortAttributes.s_id.value = new ASN1ObjectIdentifier(new int[]{1, 2, 840, 10003, 3, 1});
                sortElement.c_generic.c_sortAttributes.s_list = new AttributeList();
                sortElement.c_generic.c_sortAttributes.s_list.value = new AttributeElement[parameter.attributes.size()];
                int j = 0;
                for (SortAttribute attribute : parameter.attributes) {
                    sortElement.c_generic.c_sortAttributes.s_list.value[j].attributeType = new ASN1Integer(attribute.type);
                    sortElement.c_generic.c_sortAttributes.s_list.value[j].attributeValue = new AttributeElementAttributeValue();
                    sortElement.c_generic.c_sortAttributes.s_list.value[j].attributeValue.numeric = new ASN1Integer(attribute.value);
                    j++;
                }
            } else if (parameter.elementSetName != null) {
                sortElement.c_generic.c_elementSpec = new Specification();
                sortElement.c_generic.c_elementSpec.s_elementSpec = new SpecificationElementSpec();
                sortElement.c_generic.c_elementSpec.s_elementSpec.c_elementSetName = new InternationalString();
                sortElement.c_generic.c_elementSpec.s_elementSpec.c_elementSetName.value =
                        new ASN1GeneralString(parameter.elementSetName);
            } else {
                throw new IllegalArgumentException("no sortfield, no element set name, and no attributes given in sort request");
            }
            sortKeySpec[i] = new SortKeySpec();
            sortKeySpec[i].s_sortElement = sortElement;
            sortKeySpec[i].s_sortRelation = new ASN1Integer(parameter.ascending ? 0 : 1); // 0 = ascending, 1 = descending
            sortKeySpec[i].s_caseSensitivity = new ASN1Integer(parameter.caseSensitive ? 0 : 1); // 0 = case sensitive, 1 case insensitive
            sortKeySpec[i].s_missingValueAction = new SortKeySpecMissingValueAction();
            if (parameter.missingValue != null) {
                sortKeySpec[i].s_missingValueAction.c_missingValueData = new ASN1OctetString(parameter.missingValue);
            } else {
                if (parameter.abort) {
                    sortKeySpec[i].s_missingValueAction.c_abort = new ASN1Null();
                } else {
                    sortKeySpec[i].s_missingValueAction.c_null = new ASN1Null();
                }
            }
            i++;
        }
        sortRequest.s_sortSequence = sortKeySpec;
        sortRequest.s_sortedResultSetName = new InternationalString();
        sortRequest.s_sortedResultSetName.value = new ASN1GeneralString(sortResultSetName);
        sortRequest.s_referenceId = new ReferenceId();
        sortRequest.s_referenceId.value = new ASN1OctetString(referenceId);
        logger.log(Level.FINER, sortRequest.toString());
        write(sortRequest);
        SortResponse sortResponse = read();
        if (sortResponse != null) {
            if (sortResponse.s_sortStatus.get() == SortResponse.E_success) {
                return true;
            } else {
                DiagRec[] diagRecs = sortResponse.s_diagnostics;
                logger.log(Level.WARNING, "sort failed with diags = " + Arrays.asList(diagRecs));
                for (DiagRec diagRec : diagRecs) {
                    throw new Diagnostics(diagRec.defaultFormat.condition.get(),
                            diagRec.defaultFormat.addinfo.v3Addinfo.value.get());
                }
            }
        }
        return false;
    }

    public static class SortParameter {

        String sortField;

        List<SortAttribute> attributes;

        String elementSetName;

        boolean ascending;

        boolean caseSensitive;

        String missingValue;

        boolean abort;

        public SortParameter() {
            this.sortField = null;
            this.attributes = null;
            this.elementSetName = null;
            this.ascending = true;
            this.caseSensitive = true;
            this.abort = false;
            this.missingValue = null;
        }

        public static SortParameter of(String sortField) {
            return new SortParameter().sortField(sortField);
        }

        public SortParameter sortField(String sortField) {
            this.sortField = sortField;
            return this;
        }

        public SortParameter setAttributes(List<SortAttribute> attributes) {
            this.attributes = attributes;
            return this;
        }

        public SortParameter setAscending(boolean ascending) {
            this.ascending = ascending;
            return this;
        }

        public SortParameter setCaseSensitive(boolean caseSensitive) {
            this.caseSensitive = caseSensitive;
            return this;
        }

        public SortParameter setMissingValue(String missingValue) {
            this.missingValue = missingValue;
            return this;
        }

        public SortParameter abortOnMissingValue(boolean abort) {
            this.abort = abort;
            return this;
        }

        public SortParameter setElementSetName(String elementSetName) {
            this.elementSetName = elementSetName;
            return this;
        }
    }

    public static class SortAttribute {
        int type;

        int value;

        public SortAttribute(int type, int value) {
            this.type = type;
            this.value = value;
        }
    }
}
