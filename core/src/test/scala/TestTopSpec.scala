import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import io.circe.generic.extras.auto._
import io.circe.generic.extras.Configuration
import io.circe.parser.decode
import scala.io.Source
import java.io.File

case class TestData(name: String, rom: Seq[Int], ram: Seq[Int] = Seq(0), result: Int)

class TestTopTester(c: TestTop, res: Int) extends PeekPokeTester(c) {
    var end = false
    var cycle = 0
    while (peek(c.io.finish) == 0 && !end){
        if (cycle > 5000)
        {
           end = true 
        }
        step(1)
        cycle += 1
    }

    // check c.io.finish and results are never changed
    for (i <- 0 until 10){
        expect(c.io.finish, true)
        expect(c.io.mainReg(8), res&0xFFFFFFFF)
        step(1)
    }

    //printf("%d cycles\n", cycle)
    //printf("x8 is %d\n", peek(c.io.mainReg(8)))
}

class TestTopSpec extends AnyFreeSpec with Matchers {
    val testDir = new File("src/test/binary/")
    implicit val customConfig: Configuration = Configuration.default.withDefaults
    testDir.listFiles().filter(f => f.getName().contains("test_c_fib_arg.json")).foreach { f =>
        val json = scala.io.Source.fromFile(f.getAbsolutePath()).mkString
        val testData = decode[TestData](json) match {
            case Right(data) => data
            case Left(error) => throw new Exception(error)
        }
        testData.name in {
            Driver.execute(Array("--backend-name", "verilator", "--generate-vcd-output", "on"), () => new TestTop(testData.rom, testData.ram)){
                c => new TestTopTester(c, testData.result)
            } should be (true)
        }
    }
}