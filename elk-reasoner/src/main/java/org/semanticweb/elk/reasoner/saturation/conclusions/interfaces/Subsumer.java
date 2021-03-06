package org.semanticweb.elk.reasoner.saturation.conclusions.interfaces;
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

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpression;

/**
 * A {@link Conclusion} representing a subsumer {@link IndexedClassExpression}
 * of the root {@link IndexedClassExpression} for which it is produced.
 * Intuitively, if a subclass axiom {@code SubClassOf(:A :B)} is derived by
 * inference rules, then a {@link Subsumer} corresponding to {@code :B} can be
 * produced for the context with root {@code :A}
 * 
 * @author Frantisek Simancik
 * @author "Yevgeny Kazakov"
 * 
 * @param <S>
 *            the type of the subsumer {@link IndexedClassExpression}
 */
public interface Subsumer<S extends IndexedClassExpression> extends Conclusion {

	/**
	 * @return the {@code IndexedClassExpression} represented by this
	 *         {@link Subsumer}
	 */
	public S getExpression();

}
