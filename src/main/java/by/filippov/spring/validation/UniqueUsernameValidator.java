package by.filippov.spring.validation;


import by.filippov.spring.repository.UserRepo;
import by.filippov.spring.domain.User;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, User> {

  public final UserRepo userRepo;

  @Override
  public void initialize(UniqueUsername constraintAnnotation) {

  }

  @Override
  public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {

    if (validUsername(user)){
      return true;
    } else {
      constraintValidatorContext.disableDefaultConstraintViolation();
      constraintValidatorContext.buildConstraintViolationWithTemplate(
          constraintValidatorContext.getDefaultConstraintMessageTemplate())
          .addPropertyNode("username").addConstraintViolation();
      return false;
    }
  }

  boolean validUsername(User user){
    if (user.getId() == null) {
      return userRepo.findByUsername(user.getUsername()) == null;
    } else {
      return user.getId() == userRepo.findByUsername(user.getUsername()).getId();
    }
  }
}
