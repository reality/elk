package org.semanticweb.elk.reasoner.indexing.hierarchy;

import org.semanticweb.elk.owl.interfaces.ElkDeclarationAxiom;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2015 Department of Computer Science, University of Oxford
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

/**
 * Represents occurrences of an {@link ElkDeclarationAxiom} in an ontology.
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
public interface IndexedDeclarationAxiom extends IndexedAxiom {

	/**
	 * @return the {@link IndexedEntity} that represents the entity of the
	 *         {@link ElkDeclarationAxiom} represented by this
	 *         {@link IndexedDeclarationAxiom}
	 * 
	 * @see ElkDeclarationAxiom#getEntity()
	 */
	public IndexedEntity getEntity();

}
