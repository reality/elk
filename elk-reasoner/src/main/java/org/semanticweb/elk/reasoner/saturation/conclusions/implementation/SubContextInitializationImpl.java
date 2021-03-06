package org.semanticweb.elk.reasoner.saturation.conclusions.implementation;

/*
 * #%L
 * ELK Reasoner
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

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectProperty;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.SubContextInitialization;
import org.semanticweb.elk.reasoner.saturation.conclusions.visitors.ConclusionVisitor;
import org.semanticweb.elk.reasoner.saturation.conclusions.visitors.SubConclusionVisitor;
import org.semanticweb.elk.reasoner.saturation.context.SubContext;

/**
 * An implementation of {@link SubContextInitialization}
 * 
 * @author "Yevgeny Kazakov"
 */
public class SubContextInitializationImpl extends AbstractConclusion implements
		SubContextInitialization {

	/**
	 * the sub-root of the {@link SubContext} that should be initialized
	 */
	private final IndexedObjectProperty subRoot_;

	public SubContextInitializationImpl(IndexedObjectProperty subRoot) {
		this.subRoot_ = subRoot;
	}

	@Override
	public <I, O> O accept(ConclusionVisitor<I, O> visitor, I input) {
		return accept((SubConclusionVisitor<I, O>) visitor, input);
	}

	@Override
	public <I, O> O accept(SubConclusionVisitor<I, O> visitor, I input) {
		return visitor.visit(this, input);
	}

	@Override
	public IndexedObjectProperty getSubRoot() {
		return subRoot_;
	}

	@Override
	public String toString() {
		return "SubInit(" + subRoot_ + ")";
	}

}
