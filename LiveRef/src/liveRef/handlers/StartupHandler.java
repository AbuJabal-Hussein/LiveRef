package liveRef.handlers;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IStartup;

import liveRef.Components.*;


public class StartupHandler implements IStartup{
	
	IWorkspace workspace;
	IResourceChangeListener listener;
	
	public static boolean shouldChange = true;
	
	@Override
	public void earlyStartup() {
		workspace = ResourcesPlugin.getWorkspace();
		listener = new IResourceChangeListener() {
			@Override
			  public void resourceChanged(IResourceChangeEvent event) {
				if(!StartupHandler.shouldChange) {
					StartupHandler.shouldChange = true;
					return;
				}
				StartupHandler.shouldChange = false;
				String deltaContent = ""; 
		        IResourceDelta delta = event.getDelta();
		        if(delta == null) {
		        	return;
		        }

		        deltaContent = ResourceManager.getJavaIResourceDeltaContent(delta);
	        
	            LiveRef liveRef = new LiveRef();
		        liveRef.findExtractMethodCandidates(deltaContent);

	        	if(liveRef.getCandidates().isEmpty()) {
	        		StartupHandler.shouldChange = true;
	        		return;
	        	}
	        	MarkersHandlerJob job = new MarkersHandlerJob("Modify File Job", ResourceManager.getCurrIFile(), liveRef);
	        	job.schedule(1500); // Schedule the job to run in 1.5 second

	        }
			
		};
		workspace.addResourceChangeListener(listener);
		
	}
}
