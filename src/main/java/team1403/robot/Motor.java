package team1403.robot;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import team1403.robot.Constants;

public class Motor extends SubsystemBase {
    private CANSparkMax m_motor;
    private DutyCycleEncoder m_absoluteEncoder;

    private double m_motorSpeed;
    private double m_angle;
    
    public boolean m_atTop;
    public boolean m_atBottom;

    public int m_setpoint = 0; // set to the bottom

    public Motor() {
        m_motor = new CANSparkMax(Constants.Motor.motorID, MotorType.kBrushless);
        m_absoluteEncoder = new DutyCycleEncoder(Constants.Motor.encoderID);

        m_motor.setIdleMode(IdleMode.kBrake);
        m_motorSpeed = 0;
    }

    public double getAngle() {
        return m_angle;
    }

    public void stopMotor() {
        setMotorSpeed(0);
    }

    public void setUp() {
        m_setpoint = Constants.Motor.upAngle;
    }

    public void setDown() {
        m_setpoint = Constants.Motor.downAngle;
    }

    public void setMotorSpeed(double speed) {
        m_motor.set(speed);
    }

    private boolean isInSafeBounds() {
        return getAngle() > 130 && getAngle() < 145;
    }

    @Override
    public void periodic() {
        m_angle = m_absoluteEncoder.getAbsolutePosition() * 360;
        
        if (m_angle < m_setpoint) {
            setMotorSpeed(0.1);
        }
        if (m_angle > m_setpoint) {
            setMotorSpeed(-0.1);
        }
        
        if (m_angle == Constants.Motor.upAngle) {
            m_atTop = true;
        }
        else {
            m_atTop = false;
        }
        if (m_angle == Constants.Motor.downAngle) {
            m_atBottom = true;
        }
        else {
            m_atBottom = false;
        }

        if (!isInSafeBounds()) {
            stopMotor();
        }

        SmartDashboard.putBoolean("At Top", m_angle == Constants.Motor.upAngle);
        SmartDashboard.putBoolean("At Bottom", m_angle == Constants.Motor.downAngle);
        SmartDashboard.putNumber("Angle", m_angle);
        SmartDashboard.putNumber("Speed", m_motorSpeed);
        SmartDashboard.putNumber("Setpoint angle", m_setpoint);
    }
}
