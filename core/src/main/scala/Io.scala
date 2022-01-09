import chisel3._

class RamPort(val conf:CoreConfig) extends Bundle {
  val readData = Input(UInt(conf.ramDataWidth.W))

  val addr = Output(UInt(conf.ramWordAddressWidth.W))
  val writeData = Output(UInt(conf.ramDataWidth.W))
  val writeEnable = Output(Bool())

  override def cloneType: this.type = new RamPort(conf).asInstanceOf[this.type]
}

class RomPort(val conf:CoreConfig) extends Bundle {
  val data = Input(UInt(conf.romDataWidth.W))

  val addr = Output(UInt(conf.romWordAddressWidth.W))

  override def cloneType: this.type = new RomPort(conf).asInstanceOf[this.type]
}
