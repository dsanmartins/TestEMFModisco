package br.ufscar.query;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.modisco.omg.kdm.code.ClassUnit;
import org.eclipse.modisco.omg.kdm.code.StorableUnit;
import org.eclipse.modisco.omg.kdm.code.impl.CodePackageImpl;
import org.eclipse.modisco.omg.kdm.structure.Component;
import org.eclipse.modisco.omg.kdm.structure.impl.StructurePackageImpl;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.ecore.OCL.Query;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.helper.OCLHelper;
import org.eclipse.ocl.pivot.model.OCLstdlib;
import org.eclipse.ocl.xtext.completeocl.CompleteOCLStandaloneSetup;
import org.eclipse.ocl.xtext.essentialocl.EssentialOCLStandaloneSetup;

import rMSAS.constraintModel.ConstraintModelPackage;
import rMSAS.constraintModel.PreCondition;



public class OCLQuery {

	public Component getComponent(ResourceSet resourceSet, Resource resource, String componentName) throws ParserException {

		OCLstdlib.install(); 
		CompleteOCLStandaloneSetup.doSetup();
		EssentialOCLStandaloneSetup.doSetup(); 
		
		String sQuery = "Component.allInstances() -> any(c|c.name = '" + componentName + "')";
		org.eclipse.ocl.ecore.OCL.initialize(resourceSet);
		OCL ocl  = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE, resource);
		OCLHelper helper = ocl.createOCLHelper();
		helper.setContext(StructurePackageImpl.eINSTANCE.getEClassifier("Component"));
		OCLExpression query = helper.createQuery(sQuery);
		Query queryEval = ocl.createQuery(query);
		Component component  = (Component) queryEval.evaluate(query);
		return component;
	}


	public ClassUnit getClass(ResourceSet resourceSet, Resource resource, String className) throws ParserException {

		String sQuery = "ClassUnit.allInstances() -> any(c|c.name = '" + className + "')";
		org.eclipse.ocl.ecore.OCL.initialize(resourceSet);
		OCL ocl  = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE, resource);
		OCLHelper helper = ocl.createOCLHelper();
		helper.setContext(CodePackageImpl.eINSTANCE.getEClassifier("ClassUnit"));
		OCLExpression query = helper.createQuery(sQuery);
		Query queryEval = ocl.createQuery(query);
		ClassUnit classUnit  = (ClassUnit) queryEval.evaluate(query);
		return classUnit;
	}
	
	public StorableUnit getAttribute(ResourceSet resourceSet, Resource resource, String attributeName) throws ParserException {

		String sQuery = "StorableUnit.allInstances() -> any(c|c.name = '" + attributeName + "')";
		org.eclipse.ocl.ecore.OCL.initialize(resourceSet);
		OCL ocl  = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE, resource);
		OCLHelper helper = ocl.createOCLHelper();
		helper.setContext(CodePackageImpl.eINSTANCE.getEClassifier("StorableUnit"));
		OCLExpression query = helper.createQuery(sQuery);
		Query queryEval = ocl.createQuery(query);
		StorableUnit storableUnit  = (StorableUnit) queryEval.evaluate(query);
		return storableUnit;
	}
	
	public PreCondition getPreCondition(ResourceSet resourceSet, Resource resource) throws ParserException {
		
		String sQuery = "PreCondition.allInstances()-> asSequence()-> first()";
		org.eclipse.ocl.ecore.OCL.initialize(resourceSet);
		OCL ocl  = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE, resource);
		OCLHelper helper = ocl.createOCLHelper();
		helper.setContext(ConstraintModelPackage.eINSTANCE.getEClassifier("PreCondition"));
		OCLExpression query = helper.createQuery(sQuery);
		Query queryEval = ocl.createQuery(query);
		PreCondition preCondition  = (PreCondition) queryEval.evaluate(query);
		return preCondition;
	}
}
