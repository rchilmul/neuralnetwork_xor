//CS4811 ARTIFICIAL INTELLIGENCE SPRING 2017
//NEURAL NETWORK PROJECT (XOR)
//Author: RAVIKUMAR CHILMULA
//Date: 1/27/2017

package neuralnetwork;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.*;
import java.util.*;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NeuralNetwork extends JFrame {
  
    static ArrayList<Integer> FinalResult= new ArrayList<Integer>();
 static ArrayList<Float> FinalInputX= new ArrayList<Float>();
 static ArrayList<Float> FinalInputY= new ArrayList<Float>();
 
    public static void main(String[] args) {
        // TODO code application logic here
    Random randomGenerator= new Random();
        float [] []wH = new float[3][3];    //Weights for hidden layer inputs
        float [] []Output = new float[11][11];
        float []wO = new float[3];          //Weights for Output layer inputs
        int[][] inputIN= {{0,0,1,1},{0,1,0,1}};//Input Training Examples(XOR)
        int[] desired={0,1,1,0};              //Desired output
        //Declaration of 3layers(input,hidden,output) with IN and OUT of each perceptron
        float[] inputOUT = new float[3], hiddenIN = new float[2];
        float [] hiddenOUT = new float[3], deltaH = new float[2] ;
        float outputIN, outputOUT;
        int bias=1;                    //Considering bias
        System.out.println("Feed Forward Neuron Network ");
        //Initialisation of Weights with a random generator
        wH[0][0] =0;
        wH[0][1] =0;
        wH[1][0] =0;
        wH[1][1] =0;
        wH[2][0] =0;
        wH[2][1] =0;
        wO[0] =0;
        wO[1] =0;
        wO[2] =0;
        
        int max=3;
        int min=-3;
        //Hidden Layer
        for(int i=0; i<3; i++)//Index for the "from"(sending) perceptron(including bias)
        {
            for(int j=0; j<2; j++)//Index for the "to"(recieving) perceptron
            {
                int randomInt = randomGenerator.nextInt((max - min) + 1) + min;
                wH[i][j]=randomInt;
            }
        }
        //Output Layer
        for(int i=0; i<3; i++)//Index for "from"(sending) perceptron(including bias)
        {
            int randomInt = randomGenerator.nextInt((max - min) + 1) + min;
            wO[i]=randomInt;
        }

        int iteration=0;//Intialisation of iteration count
        float error=0;
        do
        {
            iteration = iteration +1;
            //Feed Forward Neuron Network(First Pass)
            for(int c=0; c<4; c++)//(Considering different pair of inputs(x & y))
            {
                //Input Layer Execution
                for(int r=0; r<2; r++)
                {
                    inputOUT [r]=inputIN [r][c];
                }
                inputOUT[2]=bias;
                
                //Hidden Layer Execution
                for(int j=0; j<2; j++)
                {
                    //Weighted Sum of Inputs for each Perceptron(Hidden Layer)
                    float sum=0;
                    for(int i=0; i<3; i++)
                    {
                        sum=sum+ inputOUT [i]*wH [i][j];
                    }
                    hiddenIN[j]= sum;
                    //Sigmoid Function
                    hiddenOUT[j]=(float)(1/( 1 + Math.pow(Math.E,(-1*hiddenIN[j]))));
                    
                }
                hiddenOUT[2]=bias;

                //Output Layer Execution\
                //Weighted Sum of Inputs for the Perceptron(Output Layer)
                float sum=0;
                for(int i=0; i<3; i++)
                {
                    sum=sum+ hiddenOUT[i]*wO[i];
                }
                outputIN= sum;
                outputOUT=(float)(1/( 1 + Math.pow(Math.E,(-1*outputIN))));
                //Assign delta for OUTlayer
                float deltaO=outputOUT*(1-outputOUT)*(desired[c]-outputOUT); //Calculating delta
                error=desired[c]-outputOUT;
                
                //Assign delta for nodes in Hidden layer
                for(int i=0; i<2; i++)
                {
                    deltaH[i]=hiddenOUT[i]*(1-hiddenOUT[i])*wO[i]*deltaO;
                }
                
                //Update new weights(Hidden layer)
                float constant=1;
                for(int i=0; i<3; i++)
                {
                    for(int j=0; j<2; j++)
                    {
                        wH[i][j]=wH[i][j]+(constant*inputOUT[i]*deltaH[j]);
                    }
                }
                
                //Assign new weights(Output layer)
                for(int i=0; i<3; i++)
                {
                    wO[i] = wO[i]+(constant*deltaO*hiddenOUT[i]);
                }
                /*System.out.println("Feed Forward output is: "+outputOUT);
                System.out.println("Desired output is: "+d[c]);
                System.out.println("error(delta) is: "+delta);*/
                System.out.println("Iteration count="+iteration+";error="+error);
                      
            } 
        }while(iteration<30000);//deltaO not equal to zero[0 0 0 0] or iteration less than 200

        //TESTING THE NETWORK
System.out.println("TESTING THE NETWORK");
System.out.println("TEST RESULTS:");
        //Test data

        for(int c=0; c<11; c++)
        {
            for(int r=0; r<11; r++)
            {
                inputOUT[0]=(float)(c*0.1);
                inputOUT[1]=(float)(r*0.1);
                inputOUT[2]=bias;

                //Hidden Layer Execution
                for(int j=0; j<2; j++)
                {
                    //Weighted Sum of Inputs for each Perceptron(Hidden Layer)
                    float sum=0;
                    for(int i=0; i<3; i++)
                    {
                        sum=sum+ inputOUT [i]*wH [i][j];
                    }
                    hiddenIN[j]= sum;
                    //Sigmoid Function
                    hiddenOUT[j]=(float)(1/( 1 + Math.pow(Math.E,(-1*hiddenIN[j]))));
                }
                hiddenOUT[2]=bias;

                //Output Layer Execution\
                //Weighted Sum of Inputs for the Perceptron(Output Layer)
                float sum=0;
                for(int i=0; i<3; i++)
                {
                    sum=sum+ hiddenOUT[i]*wO[i];
                }
                outputIN= sum;
                //Thresholding for Test Inputs
                int result;
                if(sum>0)
                {
                    result=1;
                }
                else
                {
                    result=0;
                }
                Output[c][r]=result;
                System.out.println("The Test result for "+"x="+inputOUT[0]+";y="+inputOUT[1]+" is "+result);     
                FinalResult.add(result);
                FinalInputX.add(inputOUT[0]);
                FinalInputY.add(inputOUT[1]);
            }
            System.out.println("trained Weight from Input Node 1 to Hidden Node 1 is "+wH[0][0]);
            System.out.println("trained Weight from Input Node 1 to Hidden Node 2 is "+wH[0][1]);
            System.out.println("trained Weight from Input Node 2 to Hidden Node 1 is "+wH[1][0]);
            System.out.println("trained Weight from Input Node 2 to Hidden Node 2 is "+wH[1][1]);
            System.out.println("Bias Input for Hidden Node 1 is "+wH[2][0]);
            System.out.println("Bias Input for Hidden Node 2 is "+wH[2][1]);
            System.out.println("trained Weight from Hidden Node 1 to Output Node is "+wO[0]);
            System.out.println("trained Weight from Hidden Node 2 to Output Node is "+wO[1]);     
            System.out.println("Bias Input for Output Node is "+wO[2]);
            System.out.println("The  Test Inputs x and y are as in individul cells"); 
            System.out.println("Yellow Represents Output '0' and Green  Represents Output '1'");
            
        }
        Plot_graph();
    }
//Plotiing the graph(XOR function with inputs with increment 0.1)
static void Plot_graph()
{
    
    JFrame f= new JFrame();
    JPanel jPanel1 = new JPanel();
    JTextField jTextField1;
    JPanel p = new JPanel();
    jPanel1.setLayout(new GridLayout(11, 11));

        for (int i = 0; i < 11 * 11; i++) {
    
            jTextField1 = new JTextField();
            if(FinalResult.get(i)==0)
            jTextField1.setBackground(Color.yellow);//Yellow Represents Output '0'
            else
            jTextField1.setBackground(Color.green);//Green Represents Output '1'
            jTextField1.setText(Float.toString(FinalInputX.get(i))+" , "+Float.toString(FinalInputY.get(i)));
            jPanel1.add(jTextField1);
        }
        f.add(jPanel1);
        f.setSize(600,600);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
    }       
}
