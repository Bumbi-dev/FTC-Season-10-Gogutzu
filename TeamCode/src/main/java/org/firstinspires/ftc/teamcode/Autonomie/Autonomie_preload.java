package org.firstinspires.ftc.teamcode.Autonomie;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Autonomie.AutonomHardware;

@Autonomous(name="...", group="Bubu")
public class Autonomie_preload extends AutonomHardware {

    float nr, viteza = 0.2f;

    Orientation angles;

    @Override
    public void runOpMode() {
        nr = 1;

        init(hardwareMap);

        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.RIGHT;

        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);

        BHI260IMU.Parameters parameters = new BHI260IMU.Parameters(orientationOnRobot);

        imu.initialize(parameters);

        angles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        telemetry.addData("Heading: ", angles.firstAngle);

        telemetry.addLine('\n' + motorFS.getCurrentPosition() + " " + motorFD.getCurrentPosition() + "\n" + motorSS.getCurrentPosition() + " " + motorSD.getCurrentPosition());

        telemetry.update();

        waitForStart();

        ElapsedTime runtime = new ElapsedTime();

        while (opModeIsActive()) {

            if(gamepad1.dpad_up && runtime.milliseconds() >= 200) {
                viteza += 0.1;
                runtime.reset();
            }

            if(gamepad1.dpad_down && runtime.milliseconds() >= 200) {
                viteza -= 0.1;
                runtime.reset();
            }

            if(gamepad1.start)
                return;

            nr -= gamepad1.left_stick_y / 100;

            telemetry.addLine("Position: " + nr + '\n' +
                                "Viteza: " + viteza);
            telemetry.update();

            if(gamepad1.a)
                goTo((int) nr);

            if(gamepad1.b)
                strafeTo((int) nr);

            if(gamepad1.y)
                goTo(0);
            
            if(gamepad1.x)
                square();
        }
    }

    public void goTo(int x) {//fata spate
        for(DcMotor motor : motoare)
            motor.setTargetPosition(x);

        for(DcMotor motor : motoare)
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        moveStraight(viteza);
        if(motorFD.getTargetPosition() - motorFD.getCurrentPosition() <= 200) {//poate ar trb verificate toate motoarele
            moveStraight(viteza / 2);
        }

        while(motoare[1].getCurrentPosition() != x && opModeIsActive()) {//inlocuire cu atTargetPosition pt mai multa acuratete dar mai putina viteza
            sleep(1);//poate ii mai precis cu val mica
        }

        frana();
    }

    public void strafeTo(int x) {//se deplaseaza lateral
        x = -x;//daca ii cu plus merge spre dreapta ca intro axa xOy

        for (DcMotor motor : motoare) {
            motor.setTargetPosition(x);
            x = -x;
        }


        moveStraight(viteza);
        if(motorFD.getTargetPosition() - motorFD.getCurrentPosition() <= 200) {//poate ar trb verificate toate motoarele
            moveStraight(viteza / 2);
        }

        for(DcMotor motor : motoare)
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(!atTargetPosition(x) && opModeIsActive()) {
            sleep(1); //poate ii mai precis cu val mica
        }

        frana();
    }

    private boolean atTargetPosition(int x) {
        for(DcMotor motor : motoare) {
            if (Math.abs(motor.getCurrentPosition() - x) > 2) {//valoare mai mare pt marja de eroare
                return false;
            }
            x = -x;
        }

        return true;
    }

    public void toTargetPosition(int x, int y) {

    }

    public void square() {

        telemetry.addLine("Patratele");
        telemetry.update();

        while(opModeIsActive()) {
            goTo(2000);

            for(DcMotor motor : motoare)
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            if(gamepad1.right_bumper && gamepad1.left_bumper)
                break;

            strafeTo(1200);

            for(DcMotor motor : motoare)
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            if(gamepad1.right_bumper && gamepad1.left_bumper)
                break;

            goTo(-2000);

            for(DcMotor motor : motoare)
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            if(gamepad1.right_bumper && gamepad1.left_bumper)
                break;

            strafeTo(-1200);

            for(DcMotor motor : motoare)
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            if(gamepad1.right_bumper && gamepad1.left_bumper)
                break;
        }
        telemetry.addLine("gata");
        telemetry.update();
    }
}
