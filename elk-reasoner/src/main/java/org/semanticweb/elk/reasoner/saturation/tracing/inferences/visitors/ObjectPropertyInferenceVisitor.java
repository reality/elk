/**
 * 
 */
package org.semanticweb.elk.reasoner.saturation.tracing.inferences.visitors;
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

import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.LeftReflexiveSubPropertyChainInference;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.PropertyChainInitialization;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.BottomUpPropertySubsumptionInference;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.ReflexivePropertyChainInference;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.ReflexiveToldSubObjectProperty;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.RightReflexiveSubPropertyChainInference;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.TopDownPropertySubsumptionInference;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.ToldReflexiveProperty;

/**
 * @author Pavel Klinov
 *
 * pavel.klinov@uni-ulm.de
 */
public interface ObjectPropertyInferenceVisitor<I, O> {

	public O visit(TopDownPropertySubsumptionInference inference, I input);
	
	public O visit(BottomUpPropertySubsumptionInference inference, I input);
	
	public O visit(PropertyChainInitialization inference, I input);
	
	public O visit(ToldReflexiveProperty inference, I input);
	
	public O visit(ReflexiveToldSubObjectProperty inference, I input);
	
	public O visit(ReflexivePropertyChainInference inference, I input);
	
	public O visit(LeftReflexiveSubPropertyChainInference inference, I input);
	
	public O visit(RightReflexiveSubPropertyChainInference inference, I input);
	
	public static ObjectPropertyInferenceVisitor<?, ?> DUMMY = new ObjectPropertyInferenceVisitor<Void, Void>() {

		@Override
		public Void visit(PropertyChainInitialization inference, Void input) {
			// no-op
			return null;
		}

		@Override
		public Void visit(ToldReflexiveProperty inference, Void input) {
			// no-op
			return null;
		}

		@Override
		public Void visit(ReflexiveToldSubObjectProperty inference, Void input) {
			// no-op
			return null;
		}

		@Override
		public Void visit(ReflexivePropertyChainInference inference, Void input) {
			// no-op
			return null;
		}

		@Override
		public Void visit(LeftReflexiveSubPropertyChainInference inference,
				Void input) {
			// no-op
			return null;
		}

		@Override
		public Void visit(RightReflexiveSubPropertyChainInference inference,
				Void input) {
			// no-op
			return null;
		}

		@Override
		public Void visit(TopDownPropertySubsumptionInference inference, Void input) {
			// no-op
			return null;
		}

		@Override
		public Void visit(BottomUpPropertySubsumptionInference inference, Void input) {
			// no-op
			return null;
		}
		
	};
}
