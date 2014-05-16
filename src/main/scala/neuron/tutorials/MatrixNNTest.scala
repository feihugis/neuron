package neuron.tutorials
import breeze.stats.distributions._
import neuron.core._
import neuron.math._
import neuron.matnn._

object MatrixNNTest extends Optimizable with Workspace {
  def main(args: Array[String]): Unit = {
    val inputTensorDimension = 10
    val outputTensorDimension= 10
    //nn = new BiLinearSymmetricNN(inputTensorDimension,outputTensorDimension).create()
    val a = new RegularizedBiLinearSymNN(inputTensorDimension,outputTensorDimension, 0.1).create()
    nn = (a TIMES a).create()
    val numOfSamples = 1
    xData = new Array(numOfSamples); yData = new Array(numOfSamples)
	for (i<- 0 until numOfSamples) {
	  val xIn = new NeuronVector(inputTensorDimension, new Uniform(-1,1))
	  val yOut= new NeuronVector(outputTensorDimension, new Uniform(-1,1))
	  xData(i) =  (xIn CROSS xIn).vec()
	  yData(i) =  (yOut CROSS yOut).vec()
	}
    val w = getRandomWeightVector()		
    
    // compute objective and gradient
    var time = System.currentTimeMillis();
	val (obj, grad) = getObjAndGrad(w)
	println(System.currentTimeMillis() - time, obj, grad.data)
	
	// gradient checking
	time = System.currentTimeMillis()
    val (obj2, grad2) = getApproximateObjAndGrad(w)
	println(System.currentTimeMillis() - time, obj2, grad2.data)
  }
}
