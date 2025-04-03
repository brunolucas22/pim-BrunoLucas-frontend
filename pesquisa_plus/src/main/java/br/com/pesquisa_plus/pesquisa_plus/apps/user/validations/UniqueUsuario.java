package br.com.pesquisa_plus.pesquisa_plus.apps.user.validations;

// Importações necessárias
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// Anotações para a validação
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsuario.UniqueValidator.class)
public @interface UniqueUsuario {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String coluna();

    public class UniqueValidator implements ConstraintValidator<UniqueUsuario, String> {

        private String coluna;

        // Adição de dependências
        @Autowired
        private UserRepository UserRepository;

        @Override
        public void initialize(UniqueUsuario constraintAnnotation) {
            coluna = constraintAnnotation.coluna();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {

            try {

                String methodName = "findBy" + Character.toUpperCase(coluna.charAt(0)) + coluna.substring(1);

                UserModel usuario = (UserModel) UserRepository.getClass()
                        .getMethod(methodName, String.class).invoke(UserRepository, value);
                if (usuario != null) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(coluna + " já existente na base de dados!")
                            .addConstraintViolation();
                    return false;
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false; // Em caso de erro, considera inválido
            }
        }

    }

}
