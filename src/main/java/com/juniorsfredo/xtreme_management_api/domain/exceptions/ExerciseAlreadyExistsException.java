package com.juniorsfredo.xtreme_management_api.domain.exceptions;

public class ExerciseAlreadyExistsException extends RuntimeException {
  public ExerciseAlreadyExistsException(String message) {
    super(message);
  }
}
