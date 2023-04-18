package liveRef.handlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

public abstract class ResourceManager {
	
	private static String fileContent = "";
	private static IResource currResource;
	private static IFile currIFile;
	
	public static IFile getCurrIFile() {
		return currIFile;
	}

	public static String getJavaIResourceDeltaContent(IResourceDelta delta) {
		fileContent = "";
        
		IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
			@Override
		    public boolean visit(IResourceDelta delta) {
		        IResource resource = delta.getResource();
		        if (resource instanceof IFile && "java".equals(resource.getFileExtension())) {
		            IFile file = (IFile) resource;
		            currResource = resource;
		            currIFile = file;
		            String result;
					try {
						result = new BufferedReader(new InputStreamReader(file.getContents()))
								   .lines().collect(Collectors.joining("\n"));
						fileContent = result;
					} catch (Exception e) {
						e.printStackTrace();
					}
		        }
		        return true;
		    }
		};
		try {
		    delta.accept(visitor);
		    
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return fileContent;
	}

	public static IResource getCurrResource() {
		return currResource;
	}

	public static String getFileContent() {
		return fileContent;
	}

}
