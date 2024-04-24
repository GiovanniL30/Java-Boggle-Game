package App;


/**
* App/ControllerPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl
* Wednesday, April 24, 2024 8:30:54 PM SGT
*/

public abstract class ControllerPOA extends org.omg.PortableServer.Servant
 implements App.ControllerOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("receiveUpdates", new java.lang.Integer (0));
    _methods.put ("sendUpdates", new java.lang.Integer (1));
    _methods.put ("updatePlayerListView", new java.lang.Integer (2));
    _methods.put ("setWaitingTime", new java.lang.Integer (3));
    _methods.put ("setGameTime", new java.lang.Integer (4));
    _methods.put ("setRound", new java.lang.Integer (5));
    _methods.put ("updatePlayerScore", new java.lang.Integer (6));
    _methods.put ("receiveLetter", new java.lang.Integer (7));
    _methods.put ("endGameUpdate", new java.lang.Integer (8));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // App/Controller/receiveUpdates
       {
         App.ClientActions clientActions = App.ClientActionsHelper.read (in);
         this.receiveUpdates (clientActions);
         out = $rh.createReply();
         break;
       }

       case 1:  // App/Controller/sendUpdates
       {
         App.ClientActions clientActions = App.ClientActionsHelper.read (in);
         this.sendUpdates (clientActions);
         out = $rh.createReply();
         break;
       }

       case 2:  // App/Controller/updatePlayerListView
       {
         this.updatePlayerListView ();
         out = $rh.createReply();
         break;
       }

       case 3:  // App/Controller/setWaitingTime
       {
         int time = in.read_long ();
         this.setWaitingTime (time);
         out = $rh.createReply();
         break;
       }

       case 4:  // App/Controller/setGameTime
       {
         int time = in.read_long ();
         this.setGameTime (time);
         out = $rh.createReply();
         break;
       }

       case 5:  // App/Controller/setRound
       {
         int round = in.read_long ();
         this.setRound (round);
         out = $rh.createReply();
         break;
       }

       case 6:  // App/Controller/updatePlayerScore
       {
         String id = in.read_string ();
         int newScore = in.read_long ();
         this.updatePlayerScore (id, newScore);
         out = $rh.createReply();
         break;
       }

       case 7:  // App/Controller/receiveLetter
       {
         String letters[] = App.LettersHelper.read (in);
         this.receiveLetter (letters);
         out = $rh.createReply();
         break;
       }

       case 8:  // App/Controller/endGameUpdate
       {
         String winner = in.read_string ();
         int score = in.read_long ();
         this.endGameUpdate (winner, score);
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:App/Controller:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Controller _this() 
  {
    return ControllerHelper.narrow(
    super._this_object());
  }

  public Controller _this(org.omg.CORBA.ORB orb) 
  {
    return ControllerHelper.narrow(
    super._this_object(orb));
  }


} // class ControllerPOA
