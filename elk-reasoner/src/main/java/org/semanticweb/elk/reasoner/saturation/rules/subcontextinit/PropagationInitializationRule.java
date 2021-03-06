package org.semanticweb.elk.reasoner.saturation.rules.subcontextinit;

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

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectSomeValuesFrom;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.Propagation;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.SubContextInitialization;
import org.semanticweb.elk.reasoner.saturation.context.ContextPremises;
import org.semanticweb.elk.reasoner.saturation.context.SubContext;
import org.semanticweb.elk.reasoner.saturation.rules.ConclusionProducer;

/**
 * 
 * A {@link SubContextInitRule} generating {@link Propagation}s when
 * initializing {@link SubContext}s
 * 
 * @author "Yevgeny Kazakov"
 */
public class PropagationInitializationRule extends AbstractSubContextInitRule {

	public static final String NAME = "Propagations For SubContext";

	private static final PropagationInitializationRule INSTANCE_ = new PropagationInitializationRule();

	public static PropagationInitializationRule getInstance() {
		return INSTANCE_;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void accept(SubContextInitRuleVisitor visitor,
			SubContextInitialization premise, ContextPremises premises,
			ConclusionProducer producer) {
		visitor.visit(this, premise, premises, producer);
	}

	@Override
	public void apply(SubContextInitialization premise,
			ContextPremises premises, ConclusionProducer producer) {
		IndexedObjectSomeValuesFrom.Helper.generatePropagations(
				premise.getSubRoot(), premises, producer);
	}

}
