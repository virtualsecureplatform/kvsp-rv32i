import scala.math.log
import scala.math.ceil

case class CoreConfig() {
    val romByteCapacity = 4096
    val romWordLength = 32
    val romDataWidth = romWordLength
    val romBytesPerWord = romWordLength / 8
    val romWordAddressWidth = ceil(log(romByteCapacity/romBytesPerWord) / log(2.0)).asInstanceOf[Int]

    val ramByteCapacity = 1024
    val ramWordLength = 32
    val ramDataWidth = ramWordLength
    val ramBytesPerWord = ramWordLength / 8
    val ramWordAddressWidth = ceil(log(ramByteCapacity/ramBytesPerWord) / log(2.0)).asInstanceOf[Int]

    var debug = true
}