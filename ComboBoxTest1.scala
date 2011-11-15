import java.awt.Insets
import swing.{GridBagPanel, ComboBox, MainFrame, SimpleSwingApplication}
import swing.event.SelectionChanged

/**
 * Created by IntelliJ IDEA.
  * Date: 11/11/15
 * Time: 12:57
 * To change this template use File | Settings | File Templates.
 */

object ComboBoxTest1 extends SimpleSwingApplication {

  def prepareList(s:String) : Either[String, List[String]] = {
    try{ // This is a sample code, actually no exception occurs.
      val l = List("0","1","2","3","4")
      Right((for (e <- l) yield s+e).toList)
    } catch { case e:Exception =>
      Left(e.getMessage)
    }
  }
  def top = new MainFrame { frame =>
    title = "ComboBox Test"
    val comboA = new ComboBox(List("A","B","C","D","E"))
    val comboB = new ComboBox((prepareList(comboA.selection.item) match {
      case Right(f) =>
        f
      case Left(f) =>
        List("---")
    }).asInstanceOf[List[String]]){
      listenTo(comboA.selection)
      reactions += {
        case SelectionChanged(comboA : ComboBox[List[String]]) => {
          println("Selection of ComboBox A is now -> "+comboA.selection.item)
          val li = (prepareList(comboA.selection.item.mkString) match { // error: type mismatch;
                                                                // found   : List[String]
                                                                // required: String
            case Right(f) =>
              f
            case Left(f) =>
              List("--")
          })
          peer.setModel(ComboBox.newConstantModel(li))
         }
      }

    }

    contents = new GridBagPanel {
      val c1 = pair2Constraints(0,0)
      c1.insets = new Insets(0,10,0,10)
      layout += comboA -> c1
      layout += comboB -> pair2Constraints (1,0)
    }
  }

}