package org.semanticweb.elk.reasoner.saturation.conclusions.implementation;
/*
 * #%L
 * ALC Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2014 Department of Computer Science, University of Oxford
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

import org.semanticweb.elk.alc.indexing.hierarchy.IndexedObjectProperty;
import org.semanticweb.elk.alc.saturation.Root;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.ExternalDeterministicConclusion;
import org.semanticweb.elk.reasoner.saturation.conclusions.visitors.ExternalConclusionVisitor;
import org.semanticweb.elk.reasoner.saturation.conclusions.visitors.ExternalDeterministicConclusionVisitor;

public abstract class AbstractPropagatedDeterministicConclusion extends
		AbstractPropagatedConclusion implements ExternalDeterministicConclusion {

	public AbstractPropagatedDeterministicConclusion(
			IndexedObjectProperty relation, Root sourceRoot) {
		super(relation, sourceRoot);
	}

	@Override
	public <I, O> O accept(ExternalConclusionVisitor<I, O> visitor, I input) {
		return accept((ExternalDeterministicConclusionVisitor<I, O>) visitor,
				input);
	}
}