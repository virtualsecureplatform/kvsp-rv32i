import chisel3._
import chisel3.util.HasBlackBoxResource

class picorv32 extends BlackBox with HasBlackBoxResource {
  val io = IO(new Bundle {
      val clk = Input(Clock())
      val resetn = Input(Bool())
      val trap = Output(Bool())

      val mem_valid = Output(Bool())
      val mem_instr = Output(Bool())
      val mem_ready = Input(Bool())

      val mem_addr = Output(UInt(32.W))
      val mem_wdata = Output(UInt(32.W))
      val mem_wstrb = Output(UInt(4.W))
      val mem_rdata = Input(UInt(32.W))
      
      val mem_la_read = Output(Bool())
      val mem_la_write = Output(Bool())
      val mem_la_addr = Output(UInt(32.W))
      val mem_la_wdata = Output(UInt(32.W))
      val mem_la_wstrb = Output(UInt(4.W))

      val pcpi_valid = Output(Bool())
      val pcpi_insn = Output(UInt(32.W))
      val pcpi_rs1 = Output(UInt(32.W))
      val pcpi_rs2 = Output(UInt(32.W))
      val pcpi_wr = Input(Bool())
      val pcpi_rd = Input(UInt(32.W))
      val pcpi_wait = Input(Bool())
      val pcpi_ready = Input(Bool())

      val irq = Input(UInt(32.W))
      val eoi = Output(UInt(32.W))

      val regs = Output(Vec(32, UInt(32.W)))
  })
  addResource("/picorv32-vsp.v")
}