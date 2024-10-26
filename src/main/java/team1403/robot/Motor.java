package team1403.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

import com.ctre.phoenix6.hardware.TalonFX;

import team1403.robot.Constants;

public class Motor extends SubsystemBase {
    private VictorSP m_motor;
    private DigitalInput m_topLimit;
    private DigitalInput m_bottomLimit;

    private double m_motorSpeed;
    
    public boolean m_atTop;
    public boolean m_atBottom;

    public String m_target = "top";

    public Motor() {
        m_motor = new VictorSP(Constants.Motor.motorID);
        m_motor.setInverted(true);
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
        if (m_topLimit.get()) {
            setMotorSpeed(Constants.Motor.speed);
            m_target = "top";
        }
    }

    public void setDown() {
        if (m_bottomLimit.get()) {
            setMotorSpeed(-1 * Constants.Motor.speed);
            m_target = "bottom";
        }
    }

    public void setMotorSpeed(double speed) {
        m_motor.set(speed);
    }

    @Override
    public void periodic() {

        //if(!m_topLimit.get() || !m_bottomLimit.get()) setMotorSpeed(0);

        getSpeed();

        if(!m_topLimit.get()) setMotorSpeed(MathUtil.clamp(m_motorSpeed, -Constants.Motor.speed, 0));
        else if(!m_bottomLimit.get()) setMotorSpeed(MathUtil.clamp(m_motorSpeed, 0, Constants.Motor.speed));

        SmartDashboard.putBoolean("top", m_atTop);
        SmartDashboard.putBoolean("bottom", m_atBottom);

        
        SmartDashboard.putBoolean("At Top", !m_topLimit.get());
        SmartDashboard.putBoolean("At Bottom", !m_bottomLimit.get());
        SmartDashboard.putString("Target", m_target);
        SmartDashboard.putNumber("Speed", getSpeed());
    }
}
