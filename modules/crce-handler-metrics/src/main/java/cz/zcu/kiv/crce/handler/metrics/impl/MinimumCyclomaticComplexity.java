package cz.zcu.kiv.crce.handler.metrics.impl;

import javax.annotation.Nonnull;

import cz.zcu.kiv.crce.handler.metrics.ComponentMetrics;
import cz.zcu.kiv.crce.handler.metrics.asm.ClassMetrics;
import cz.zcu.kiv.crce.handler.metrics.asm.ClassesMetrics;

/**
 * Implementation of minimum of McCabe's Cyclomatic Complexity for all classes, 
 * where Cyclomatic Complexity of class is computed as average Cyclomatic 
 * Complexity of all non-abstract methods. 
 * 'A Complexity Measure' - McCabe, T.J.  (1976)
 * 
 * @author Jan Smajcl (smajcl@students.zcu.cz)
 *
 * @see <a href="http://ieeexplore.ieee.org/xpl/login.jsp?tp=&arnumber=1702388&url=http%3A%2F%2Fieeexplore.ieee.org%2Fxpls%2Fabs_all.jsp%3Farnumber%3D1702388">A Complexity Measure</a>
 */
public class MinimumCyclomaticComplexity implements ComponentMetrics {

	private ClassesMetrics classesMetrics;
	
	/**
	 * New instance.
	 * 
	 * @param classesMetrics Wrapper of parsed ClassMetrics list.
	 */
	public MinimumCyclomaticComplexity(ClassesMetrics classesMetrics) {
		
		this.classesMetrics = classesMetrics;
	}
	
	@Override
	public void init() {
		// nothing to do here
	}

	@Override
	@Nonnull
	public String getName() {
		return "design-complexity-min";
	}

	@Override
	@Nonnull
	@SuppressWarnings("rawtypes")
	public Class getType() {
		return Double.class;
	}

	@Override
	@Nonnull
	public Object computeValue() {

        double minimumCyclomaticComplexity = Double.MAX_VALUE;
        for (ClassMetrics classMetrics : classesMetrics.getClassMetricsList()) {
        	
        	double averageCyclomaticComplexity = classMetrics.getAverageCyclomaticComplexity();
        	if (!Double.isNaN(averageCyclomaticComplexity) && minimumCyclomaticComplexity > averageCyclomaticComplexity) {
        			
        		minimumCyclomaticComplexity = averageCyclomaticComplexity;
        	}
        }
       
        // we didn't find min value
        if (minimumCyclomaticComplexity == Double.MAX_VALUE) {
        	
        	minimumCyclomaticComplexity = Double.NaN;
        }
        	
		return Double.valueOf(minimumCyclomaticComplexity);
	}
}
