/**
 * 
 */
package org.semanticweb.elk.reasoner.saturation.tracing;
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

import org.semanticweb.elk.reasoner.saturation.IndexedContextRoot;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.Conclusion;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.visitors.InferenceVisitor;

/**
 * Delegates all calls to the underlying {@link TraceStore.Reader}.
 * 
 * @author Pavel Klinov
 *
 * pavel.klinov@uni-ulm.de
 */
abstract class DelegatingTraceReader implements TraceStore.Reader {

	protected final TraceStore.Reader reader;
	
	DelegatingTraceReader(TraceStore.Reader r) {
		reader = r;
	}
	
	@Override
	public void accept(IndexedContextRoot root, Conclusion conclusion,
			InferenceVisitor<?, ?> visitor) {
		reader.accept(root, conclusion, visitor);
	}

	@Override
	public Iterable<IndexedContextRoot> getContextRoots() {
		return reader.getContextRoots();
	}

	@Override
	public void visitInferences(IndexedContextRoot root, InferenceVisitor<?, ?> visitor) {
		reader.visitInferences(root, visitor);
	}

}
