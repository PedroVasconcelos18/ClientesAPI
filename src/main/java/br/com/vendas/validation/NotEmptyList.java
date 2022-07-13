package br.com.vendas.validation;

import br.com.vendas.validation.constraintValidation.NotEmptyListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // para ser verificada em tempo de execução
@Target(ElementType.FIELD) // vai dizer onde podemos colocar essa annotation
@Constraint(validatedBy = NotEmptyListValidator.class)
// aqui é oq vai dizer que é um annotation de validação, será implementada nessa interface uma classe que fará a validação
public @interface NotEmptyList {

    String message() default "A lista não pode ser vazia.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
