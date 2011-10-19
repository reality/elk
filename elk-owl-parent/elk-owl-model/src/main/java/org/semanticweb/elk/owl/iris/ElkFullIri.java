/*
 * #%L
 * ELK OWL Model Implementation
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 Department of Computer Science, University of Oxford
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
package org.semanticweb.elk.owl.iris;


/**
 * @author Frantisek Simancik
 *
 */
public class ElkFullIri extends ElkIri {
	
	protected final String iri;
	
	public ElkFullIri(String iri) {
		this.iri = iri;
	}
	
	public ElkFullIri(ElkPrefix prefix, String localName) {
		this.iri = prefix.getIri()+localName;
	}
	
	@Override
	public String toString() {
		return iri;
	}
	
}