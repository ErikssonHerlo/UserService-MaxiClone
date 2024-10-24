package com.erikssonherlo.user.application.usecase;

import com.erikssonherlo.common.application.anotation.UseCase;
import com.erikssonherlo.user.application.dto.PasswordResetCodeNotificationDTO;
import com.erikssonherlo.user.infrastructure.port.input.GenerateResetPasswordCode;
import com.erikssonherlo.user.infrastructure.port.output.UserJpaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Random;

@UseCase
@RequiredArgsConstructor
public class GenerateResetPasswordCodeUseCase implements GenerateResetPasswordCode {

    private final UserJpaRepositoryPort userJpaRepositoryPort;
    private final RabbitTemplate rabbitTemplate;

    private static final String QUEUE_NAME = "notification";
    private static final String TEMPLATE_TYPE = "VERIFICATION_CODE";

    @Override
    @Transactional
    public boolean generateResetPasswordCode(String email) {
        try {
            // Generación del código numérico de 6 dígitos
            String authCode = generateRandomAuthCode();
            LocalDateTime expirationDate = LocalDateTime.now().plusHours(3);

            // Guardar el código y la fecha de expiración en la base de datos
            userJpaRepositoryPort.generateResetPasswordCode(email, authCode, expirationDate);

            // Crear un objeto de mensaje para la cola (puede ser un DTO con más detalles si es necesario)
            PasswordResetCodeNotificationDTO notificationDTO = new PasswordResetCodeNotificationDTO(email, authCode);

            // Enviar la notificación a la cola de RabbitMQ
            //rabbitTemplate.convertAndSend(QUEUE_NAME, notificationDTO) ;

            System.out.println("Message from reset password sent to queue: " + notificationDTO);

            return true;

        } catch (Exception e) {
            // Manejo de errores
            System.err.println("Error on generate reset password code: " + e.getMessage());
            return false;
        }
    }

    /**
     * Generates a random 6-digit code
     * @return
     */
    private String generateRandomAuthCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
