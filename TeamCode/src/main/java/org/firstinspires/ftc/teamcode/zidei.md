





//mod la caterinca
boolean dublu = false;

caterinca
if(gamepad1.left_bumper)
    dublu = true;
if(gamepad1.right_bumper)
    dublu = false;

if(dublu) {
    doubleMovement();
    continue;
}

private void doubleMovement() {
motorFS.setPower(-gamepad1.left_stick_y); motorFD.setPower(-gamepad1.right_stick_y);
motorSS.setPower(-gamepad2.left_stick_y); motorSD.setPower(-gamepad2.right_stick_y);
}