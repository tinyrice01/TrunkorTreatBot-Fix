package team1403.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix6.hardware.TalonFX;

import team1403.robot.Constants;

public class Motor extends SubsystemBase {
    private TalonFX m_motor;
    private DigitalInput m_topLimit;
    private DigitalInput m_bottomLimit;

    private double m_motorSpeed;
    
    public boolean m_atTop;
    public boolean m_atBottom;

    public String m_target;

    public Motor() {
        m_motor = new TalonFX(Constants.Motor.motorID);
        m_topLimit = new DigitalInput(Constants.Motor.topLimitPort);
        m_bottomLimit = new DigitalInput(Constants.Motor.bottomLimitPort);

        m_motorSpeed = 0;
    }

    public double getSpeed() {
        m_motorSpeed = m_motor.get();
        return m_motorSpeed;
    }

    public void stopMotor() {
        setMotorSpeed(0);
    }

    public void setUp() {
        if (m_atBottom) {
            setMotorSpeed(Constants.Motor.speed);
            m_target = "top";
        }
    }

    public void setDown() {
        if (m_atTop) {
            setMotorSpeed(-1 * Constants.Motor.speed);
            m_target = "bottom";
        }
    }

    public void setMotorSpeed(double speed) {
        m_motor.set(speed);
    }

    private boolean isInSafeBounds() {
        return m_motor.get() > Constants.Motor.speed && m_motor.get() < -1 * Constants.Motor.speed;
    }

    @Override
    public void periodic() {
        if (m_topLimit.get() && m_target.equals("top")) {
            m_atTop = true;
            stopMotor();
        }
        if (!m_topLimit.get()) {
            m_atTop = false;
        }
        if (m_bottomLimit.get() && m_target.equals("bottom")) {
            m_atBottom = true;
            stopMotor();
        }
        if (!m_bottomLimit.get()) {
            m_atBottom = false;
        }
        if (!isInSafeBounds()) {
            stopMotor();
        }

        SmartDashboard.putBoolean("At Top", m_topLimit.get());
        SmartDashboard.putBoolean("At Bottom", m_bottomLimit.get());
        SmartDashboard.putString("Target", m_target);
        SmartDashboard.putNumber("Speed", getSpeed());
    }
}
