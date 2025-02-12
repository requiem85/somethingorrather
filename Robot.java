/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMax;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private TalonFX _talon0;
  private Joystick joy1;
  public CANSparkMax CSM0;
  private double boop; 
  private CANSparkMax jax;
  
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    _talon0 = new TalonFX(1);
    joy1 = new Joystick(0);
    CSM0 = new CANSparkMax(5,CANSparkMaxLowLevel.MotorType.kBrushless);
    CSM0.restoreFactoryDefaults();
    CSM0.getEncoder().getPosition();

    //NeoMotorWposlog Neo1 = new NeoMotorWposlog(1);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
   
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopInit() {
    CSM0.restoreFactoryDefaults();
    _talon0.configIntegratedSensorOffset(0);
    
    
  }
  @Override
  public void teleopPeriodic() {

    if(joy1.getRawButton(1)){
      _talon0.set(ControlMode.PercentOutput,0.1);
      CSM0.set(0.1);
    }else{
      _talon0.set(ControlMode.PercentOutput,0);
      CSM0.set(0);
    }
    if(joy1.getRawButton(3)){
      
      while(true){
        boop = CSM0.getEncoder().getPosition();
        SmartDashboard.putNumber("boop", boop);
        CSM0.set(0.1);
        SmartDashboard.putNumber("NEO", CSM0.getEncoder().getPosition());
        if(joy1.getRawButton(4)){
          

          break;
          
        }else if(boop >= 358.3){
          CSM0.set(0);
          break;
        }


        }

      }
    
      
  
    if(joy1.getRawButton(2)){
      CSM0.getEncoder().setPosition(0);
      
    }
    SmartDashboard.putNumber("NEO", CSM0.getEncoder().getPosition());
    SmartDashboard.putNumber("FALCON", _talon0.getSelectedSensorPosition());
    boop = CSM0.getEncoder().getPosition();
    SmartDashboard.putNumber("boop", boop);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
