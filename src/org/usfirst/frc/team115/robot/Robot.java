
package org.usfirst.frc.team115.robot;

import edu.wpi.first.wpilibj.GearTooth;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	public GearTooth isTrue, isFalse;
	public boolean limit, firstCall;
	public Timer timer;
	double oldValueTrue, oldValueFalse, oldTime;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	limit = false;
    	firstCall = true;
    	
		isTrue = new GearTooth(1);
		isTrue.setSemiPeriodMode(true);
		isTrue.setUpdateWhenEmpty(false);
		oldValueTrue = isTrue.get();
		
		isFalse = new GearTooth(1);
		isFalse.setSemiPeriodMode(false);
		isFalse.setUpdateWhenEmpty(false);
		oldValueFalse = isFalse.get();
		
    	timer = new Timer();
    	timer.start();
    	oldTime = timer.get();
		
    	oi = new OI();
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	
    	double newValueTrue = isTrue.get();
    	double newValueFalse = isFalse.get();
    	double newTime = timer.get();
    	
    	if ((!firstCall) && ((oldValueTrue == newValueTrue || oldValueFalse == newValueTrue) && ((oldTime-newTime) >= 0.010))) {
	    	if (newValueTrue != newValueFalse) {
	    		limit = true;
	    	} else if (newValueTrue == newValueFalse) {
	    		limit = false;
	    		if (newValueTrue >= 1.0 && newValueFalse >= 1.0){
	    			isTrue.reset();
	    			isFalse.reset();
	    		}
	    	}
    	}
    	
    	SmartDashboard.putBoolean("Limit Switch", limit);
    	oldValueTrue = newValueTrue;
    	oldValueFalse = newValueFalse;
    	oldTime = newTime;
    	firstCall = false;
    	
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
