package cz.zcu.kiv.crce.handler.metrics.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import cz.zcu.kiv.crce.handler.metrics.PackageMetrics;
import cz.zcu.kiv.crce.handler.metrics.asm.ClassMetrics;
import cz.zcu.kiv.crce.handler.metrics.asm.ClassesMetrics;
import cz.zcu.kiv.crce.handler.metrics.asm.MethodMetrics;

/**
 * Implementation of computing ripple effect. Starting from public methods of export package classes 
 * and expanding call tree until all out of components calls are found.  
 * 
 * @author Jan Smajcl (smajcl@students.zcu.cz)
 */
public class RippleEffectMetrics implements PackageMetrics {

	private ClassesMetrics classesMetrics;
	
	/**
	 * New instance.
	 * 
	 * @param classesMetrics Wrapper of parsed ClassMetrics list.
	 */
	public RippleEffectMetrics(ClassesMetrics classesMetrics) {
		
		this.classesMetrics = classesMetrics;
	}
	
	@Override
	public void init() {
		
        classesMetrics.connectCalledMethods();
	}
	
	@Override
	@Nonnull
	public String getName() {
		return "ripple-effect";
	}
	
	@Override
	@Nonnull
	@SuppressWarnings("rawtypes")
	public Class getType() {
		return Long.class;
	}
	
	
	@Override
	@Nonnull
	public Object computeValueForPackage(String packageName) {
		
		// set of all methods
		Set<MethodMetrics> collectedMethods = new HashSet<MethodMetrics>();
		// in-jar methods, which can have other method call and should be investigated 
		List<MethodMetrics> methodsToVisit = new ArrayList<MethodMetrics>();
		
		// first collect public method of public classes from package
        for (ClassMetrics classMetric : classesMetrics.getClassMetricsList()) {
        	if (classMetric.isPublic() && classMetric.getPackageName().equals(packageName)) {
        		for (MethodMetrics method : classMetric.getMethods()) {
        			if (method.isPublic()) {
        				collectedMethods.add(method);
        				methodsToVisit.add(method);
        			}
        		}	        		
        	}
        }
        
        // expand all internal methods (internal calls)
        while (!methodsToVisit.isEmpty()) {        	
        	MethodMetrics investigatedMethod = methodsToVisit.remove(0);        	
        	for (MethodMetrics methodCall : investigatedMethod.getMethodCalls()) {        		
        		if (!collectedMethods.contains(methodCall)) {
        			collectedMethods.add(methodCall);
        			if (methodCall.isInternal()) {
        				methodsToVisit.add(methodCall);
        			}
        		}	        			        		
        	}
        }
        
        int internalNonAbstract = 0;
        int internalAbstract = 0;
        int external = 0;
        
        // count internal and external methods
        for (MethodMetrics methodCall : collectedMethods) {
        	if (methodCall.isInternal()) {
        		if (methodCall.isAbstract()) {
        			internalAbstract++;
        		} 
        		else {
        			internalNonAbstract++;
        		}
        	}
        	else {
        		external++;
        	}
        }
        
        long rippleEffectValue = internalNonAbstract + internalAbstract + external;
        
        return Long.valueOf(rippleEffectValue);
	}
}
