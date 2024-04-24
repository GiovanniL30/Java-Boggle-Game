package App;


/**
* App/ClientActions.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Application.idl/
* Wednesday, April 24, 2024 7:39:09 PM SGT
*/

public class ClientActions implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 3;
  private static App.ClientActions[] __array = new App.ClientActions [__size];

  public static final int _NEW_MESSAGE = 0;
  public static final App.ClientActions NEW_MESSAGE = new App.ClientActions(_NEW_MESSAGE);
  public static final int _START_GAME = 1;
  public static final App.ClientActions START_GAME = new App.ClientActions(_START_GAME);
  public static final int _NEW_GAME_ROUND = 2;
  public static final App.ClientActions NEW_GAME_ROUND = new App.ClientActions(_NEW_GAME_ROUND);

  public int value ()
  {
    return __value;
  }

  public static App.ClientActions from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected ClientActions (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class ClientActions
