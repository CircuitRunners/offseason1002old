package org.firstimspires.ftc.teamcode

import com.arcrobotics.ftclib.command.SubsystemBase

public class something extends SubsystemBase{
    private DcMotorEx motor;

    public something(HardwareMap hardwareMap){
        motor = hardwareMap.get(DcMotorEx.class, "motor");
    }

    public void spin() {
        motor.setPower(1);
    }

    public void stop() {
        motor.setPower(0);
    }
}