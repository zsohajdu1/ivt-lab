package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.bytebuddy.matcher.FailSafeMatcher;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primaryMock;
  private TorpedoStore secondaryMock;

  @BeforeEach
  public void init(){
    primaryMock = mock(TorpedoStore.class);
    secondaryMock = mock(TorpedoStore.class);
    this.ship = new GT4500(primaryMock, secondaryMock);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primaryMock.fire(1)).thenReturn(true);
    when(primaryMock.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryMock).fire(1);
    verify(primaryMock).isEmpty();
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primaryMock.fire(1)).thenReturn(true);
    when(secondaryMock.fire(1)).thenReturn(true);
    
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryMock).fire(1);
    verify(secondaryMock).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Failure(){
    // Arrange
    when(primaryMock.isEmpty()).thenReturn(true);
    when(secondaryMock.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(primaryMock).isEmpty();
    verify(secondaryMock).isEmpty();
  }

  @Test
  public void fireTorpedo_All_Failure(){
    // Arrange
    when(primaryMock.fire(1)).thenReturn(false);
    when(secondaryMock.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(primaryMock).fire(1);
    verify(secondaryMock).fire(1);
  }

  @Test
  public void fireTorpedo_All_First_Failure(){
    // Arrange
    when(primaryMock.fire(1)).thenReturn(false);
    when(secondaryMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryMock).fire(1);
    verify(secondaryMock).fire(1);
  }

  @Test
  public void fireTorpedo_All_Second_Failure(){
    // Arrange
    when(primaryMock.fire(1)).thenReturn(true);
    when(secondaryMock.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryMock).fire(1);
    verify(secondaryMock).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_First(){
    // Arrange
    when(primaryMock.isEmpty()).thenReturn(true);
    when(secondaryMock.isEmpty()).thenReturn(false);
    when(secondaryMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    result = ship.fireTorpedo(FiringMode.SINGLE) || result;

    // Assert
    assertEquals(true, result);
    verify(secondaryMock, times(2)).isEmpty();
    verify(secondaryMock, times(2)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_Second(){
    // Arrange
    when(primaryMock.isEmpty()).thenReturn(false);
    when(secondaryMock.isEmpty()).thenReturn(true);
    when(primaryMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    result = ship.fireTorpedo(FiringMode.SINGLE) || result;

    // Assert
    assertEquals(true, result);
    verify(primaryMock, times(2)).isEmpty();
    verify(primaryMock, times(2)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_First_Second(){
    // Arrange·
    when(primaryMock.isEmpty()).thenReturn(false);
    when(primaryMock.fire(1)).thenReturn(true);
    when(secondaryMock.isEmpty()).thenReturn(false);
    when(secondaryMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);


    when(primaryMock.isEmpty()).thenReturn(true);
    when(primaryMock.fire(1)).thenReturn(false);

    result = ship.fireTorpedo(FiringMode.SINGLE) && result;

    // Assert
    assertEquals(true, result);
    verify(primaryMock, times(1)).isEmpty();
    verify(secondaryMock, times(1)).isEmpty();
    verify(primaryMock, times(1)).fire(1);
    verify(secondaryMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_Second_First(){
    // Arrange·
    when(primaryMock.isEmpty()).thenReturn(false);
    when(primaryMock.fire(1)).thenReturn(true);
    when(secondaryMock.isEmpty()).thenReturn(true);
    when(secondaryMock.fire(1)).thenReturn(false);

    // Act
    boolean firstResult = ship.fireTorpedo(FiringMode.SINGLE);


    when(primaryMock.isEmpty()).thenReturn(true);
    when(primaryMock.fire(1)).thenReturn(false);

    boolean secondResult = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, firstResult);
    assertEquals(false, secondResult);
    verify(primaryMock, times(2)).isEmpty();
    verify(secondaryMock, times(1)).isEmpty();
    verify(primaryMock, times(1)).fire(1);
  }

@Test
public void fireLaser(){
    boolean result = ship.fireLaser(FiringMode.ALL);
    assertEquals(false, result);
}  



}
