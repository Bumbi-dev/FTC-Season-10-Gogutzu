package org.firstinspires.ftc.LTASrob.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@TeleOp(name = "Incercare", group = "Bubu")
public class CasiHardware extends LinearOpMode {

    DcMotor stanga1;

    public void init(HardwareMap ahwMap) {
        stanga1 = hardwareMap.get(DcMotor.class, "motor dreapta1");
        stanga1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        stanga1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }




    @Override
    public void runOpMode() throws InterruptedException {
        //telemetry.addLine(stanga1.getCurrentPosition() + "");
        //telemetry.update();
        init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {
            if(gamepad1.left_trigger > 0) {
                stanga1.setPower(0);
                continue;
            }
            stanga1.setPower(gamepad1.right_trigger);
        }
    }
}
