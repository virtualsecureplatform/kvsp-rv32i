import chisel3._
import chisel3.util.Cat

class ExternalRam(val conf: CoreConfig, val d:Seq[Int]) extends Module {
    val io = IO(new Bundle {
      val port = Flipped(new RamPort(conf))
    })

    var initSeq = d
    for (i <- initSeq.size until conf.ramByteCapacity) {
        initSeq = initSeq :+ 0
    }
    val mem = RegInit(VecInit(initSeq map (x => x.U(8.W))))

    when(io.port.writeEnable) {
      mem(io.port.addr*4.U) := io.port.writeData(7, 0)
      mem((io.port.addr*4.U) + 1.U) := io.port.writeData(15, 8)
      mem((io.port.addr*4.U) + 2.U) := io.port.writeData(23, 16)
      mem((io.port.addr*4.U) + 3.U) := io.port.writeData(31, 24)
      printf("MEM[0x%x] <= 0x%x\n", io.port.addr, io.port.writeData)
    }

    io.port.readData := Cat(mem(io.port.addr*4.U+3.U), mem(io.port.addr*4.U+2.U), mem(io.port.addr*4.U+1.U), mem(io.port.addr*4.U))
}
