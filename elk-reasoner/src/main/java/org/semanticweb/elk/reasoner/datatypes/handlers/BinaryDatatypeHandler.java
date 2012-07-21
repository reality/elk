/*
 * #%L
 * ELK Reasoner
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 - 2012 Department of Computer Science, University of Oxford
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.semanticweb.elk.reasoner.datatypes.handlers;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.xml.bind.DatatypeConverter;
import org.apache.log4j.Logger;
import org.semanticweb.elk.owl.interfaces.ElkDataRange;
import org.semanticweb.elk.owl.interfaces.ElkDatatype;
import org.semanticweb.elk.owl.interfaces.ElkDatatypeRestriction;
import org.semanticweb.elk.owl.interfaces.ElkFacetRestriction;
import org.semanticweb.elk.reasoner.datatypes.enums.Datatype;
import static org.semanticweb.elk.reasoner.datatypes.enums.Datatype.xsd_base64Binary;
import static org.semanticweb.elk.reasoner.datatypes.enums.Datatype.xsd_hexBinary;
import org.semanticweb.elk.reasoner.datatypes.enums.Facet;
import static org.semanticweb.elk.reasoner.datatypes.enums.Facet.*;
import org.semanticweb.elk.reasoner.datatypes.valuespaces.EmptyValueSpace;
import org.semanticweb.elk.reasoner.datatypes.valuespaces.EntireValueSpace;
import org.semanticweb.elk.reasoner.datatypes.valuespaces.ValueSpace;
import org.semanticweb.elk.reasoner.datatypes.valuespaces.restricted.LengthRestrictedValueSpace;
import org.semanticweb.elk.reasoner.datatypes.valuespaces.values.BinaryValue;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedDataHasValue;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedDataSomeValuesFrom;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedDatatypeExpression;

/**
 * xsd:hexBinary and xsd:base64Binary datatype handler
 * <p>
 * uses {@link BinaryValue} and {@link LengthRestrictedValueSpace} to
 * represent datatype restrictions. Please note that value space of
 * xsd:hexBinary and xsd:base64Binary are disjoint.
 *
 * @author Pospishnyi Olexandr
 */
public class BinaryDatatypeHandler implements DatatypeHandler {

	static final Logger LOGGER_ = Logger.getLogger(BinaryDatatypeHandler.class);

	public Set<Datatype> getSupportedDatatypes() {
		return EnumSet.of(xsd_hexBinary, xsd_base64Binary);
	}

	public Set<Facet> getSupportedFacets() {
		return EnumSet.of(LENGTH, MIN_LENGTH, MAX_LENGTH);
	}

	public ValueSpace convert(IndexedDatatypeExpression datatypeExpression) {
		if (datatypeExpression instanceof IndexedDataHasValue) {
			return createLiteralValueSpace((IndexedDataHasValue) datatypeExpression);
		} else if (datatypeExpression instanceof IndexedDataSomeValuesFrom) {
			ElkDataRange filler = ((IndexedDataSomeValuesFrom) datatypeExpression).getFiller();
			if (filler instanceof ElkDatatype) {
				return new EntireValueSpace(datatypeExpression.getDatatype());
			} else {
				return createRestrictedValueSpace((ElkDatatypeRestriction) filler);
			}
		}
		LOGGER_.warn("Unsupported datatype expression: " + datatypeExpression.getClass().getName());
		return null;
	}
	
	private ValueSpace createLiteralValueSpace(IndexedDataHasValue datatypeExpression) {
		Datatype datatype = datatypeExpression.getDatatype();
		byte[] value =  (byte[]) parse(datatypeExpression.getFiller().getLexicalForm(), datatype);
		if (value != null) {
			return new BinaryValue(value, datatype);
		} else {
			return null;
		}
	}

	private ValueSpace createRestrictedValueSpace(ElkDatatypeRestriction filler) {
		Integer minLength = 0;
		Integer maxLength = Integer.valueOf(Integer.MAX_VALUE);
		Datatype datatype = Datatype.getByIri(filler.getDatatype().getDatatypeIRI());

		List<? extends ElkFacetRestriction> facetRestrictions = filler.getFacetRestrictions();
		outerloop:
		for (ElkFacetRestriction facetRestriction : facetRestrictions) {
			Facet facet = Facet.getByIri(facetRestriction.getConstrainingFacet().getFullIriAsString());
			String value = facetRestriction.getRestrictionValue().getLexicalForm();

			switch (facet) {
				case LENGTH:
					minLength = Integer.valueOf(value);
					maxLength = minLength;
					break outerloop;
				case MIN_LENGTH:
					minLength = Integer.valueOf(value);
					break;
				case MAX_LENGTH:
					maxLength = Integer.valueOf(value);
					break;
				default:
					LOGGER_.warn("Unsupported facet: " + facet.iri);
					return null;
			}

		}
		LengthRestrictedValueSpace vs = new LengthRestrictedValueSpace(datatype, minLength, maxLength);
		if (vs.isEmptyInterval()) {
			return EmptyValueSpace.INSTANCE;
		} else {
			return vs;
		}
	}

	public Object parse(String literal, Datatype datatype) {
		switch (datatype) {
			case xsd_base64Binary:
				return DatatypeConverter.parseBase64Binary(literal);
			case xsd_hexBinary:
				return DatatypeConverter.parseHexBinary(literal);
			default:
				return null;
		}
	}
}
