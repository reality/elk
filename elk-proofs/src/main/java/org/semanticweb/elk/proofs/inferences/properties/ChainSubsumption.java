/**
 * 
 */
package org.semanticweb.elk.proofs.inferences.properties;
/*
 * #%L
 * ELK Proofs Package
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

import java.util.Arrays;
import java.util.Collection;

import org.semanticweb.elk.owl.interfaces.ElkSubObjectPropertyOfAxiom;
import org.semanticweb.elk.proofs.expressions.derived.DerivedExpression;
import org.semanticweb.elk.proofs.expressions.derived.DerivedExpressionFactory;
import org.semanticweb.elk.proofs.inferences.Inference;
import org.semanticweb.elk.proofs.inferences.InferenceVisitor;
import org.semanticweb.elk.proofs.utils.InferencePrinter;

/**
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public class ChainSubsumption implements Inference {

	private final DerivedExpression firstPremise_;

	private final DerivedExpression secondPremise_;

	private final DerivedExpression conclusion_;

	private final DerivedExpression axiom_;

	// one premise and a side condition
	public ChainSubsumption(
			ElkSubObjectPropertyOfAxiom conclusion,
			ElkSubObjectPropertyOfAxiom premise, 
			ElkSubObjectPropertyOfAxiom sideCondition,
			DerivedExpressionFactory exprFactory) {
		firstPremise_ = exprFactory.create(premise);
		secondPremise_ = null;
		conclusion_ = exprFactory.create(conclusion);
		axiom_ = exprFactory.createAsserted(sideCondition);
	}

	// two premises and no side condition
	public ChainSubsumption(
			ElkSubObjectPropertyOfAxiom conclusion,
			DerivedExpression first, 
			ElkSubObjectPropertyOfAxiom second,
			DerivedExpressionFactory exprFactory) {
		firstPremise_ = first;
		secondPremise_ = exprFactory.create(second);
		conclusion_ = exprFactory.create(conclusion);
		axiom_ = null;
	}

	@Override
	public Collection<? extends DerivedExpression> getPremises() {
		if (secondPremise_ == null) {
			return Arrays.asList(firstPremise_, axiom_);
		}

		return Arrays.asList(firstPremise_, secondPremise_);
	}

	@Override
	public DerivedExpression getConclusion() {
		return conclusion_;
	}

	@Override
	public <I, O> O accept(InferenceVisitor<I, O> visitor, I input) {
		return visitor.visit(this, input);
	}

	@Override
	public String toString() {
		return InferencePrinter.print(this);
	}
}
