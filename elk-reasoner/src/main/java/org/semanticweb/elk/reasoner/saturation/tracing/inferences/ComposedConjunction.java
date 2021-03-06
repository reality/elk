/**
 * 
 */
package org.semanticweb.elk.reasoner.saturation.tracing.inferences;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2013 Department of Computer Science, University of Oxford
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

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpression;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectIntersectionOf;
import org.semanticweb.elk.reasoner.saturation.IndexedContextRoot;
import org.semanticweb.elk.reasoner.saturation.conclusions.implementation.ComposedSubsumerImpl;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.ComposedSubsumer;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.Subsumer;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.visitors.InferenceVisitor;

/**
 * A {@link ComposedSubsumer} for {@link IndexedObjectIntersectionOf}
 * obtained from its conjunts.
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public class ComposedConjunction extends
		ComposedSubsumerImpl<IndexedObjectIntersectionOf> implements Inference {

	/**
	 */
	public ComposedConjunction(IndexedObjectIntersectionOf conjunction) {
		super(conjunction);
	}

	public Subsumer<IndexedClassExpression> getFirstConjunct() {
		return new ComposedSubsumerImpl<IndexedClassExpression>(getExpression()
				.getFirstConjunct());
	}

	public Subsumer<IndexedClassExpression> getSecondConjunct() {
		return new ComposedSubsumerImpl<IndexedClassExpression>(getExpression()
				.getSecondConjunct());
	}

	@Override
	public <I, O> O acceptTraced(InferenceVisitor<I, O> visitor, I parameter) {
		return visitor.visit(this, parameter);
	}

	@Override
	public IndexedContextRoot getInferenceContextRoot(
			IndexedContextRoot rootWhereStored) {
		return rootWhereStored;
	}

	@Override
	public String toString() {
		return super.toString() + " (conjunction+)";
	}

}
