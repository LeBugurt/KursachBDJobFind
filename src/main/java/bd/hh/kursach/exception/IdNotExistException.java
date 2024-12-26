package bd.hh.kursach.exception;

import java.util.UUID;

public class IdNotExistException extends RuntimeException {
  public static final String ID_NOT_EXIST_MESSAGE = "Объект с id:%s не существует";

  public IdNotExistException(UUID id) {
    super(String.format(ID_NOT_EXIST_MESSAGE, id));
  }
}

