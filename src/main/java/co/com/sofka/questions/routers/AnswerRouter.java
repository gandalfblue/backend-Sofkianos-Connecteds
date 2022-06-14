package co.com.sofka.questions.routers;

import co.com.sofka.questions.answerusecases.AddAnswerToAnswerUseCase;
import co.com.sofka.questions.answerusecases.AddAnswerUseCase;
import co.com.sofka.questions.answerusecases.GetAnswerUseCase;
import co.com.sofka.questions.answerusecases.UpdateAnswerToAnswerUseCase;
import co.com.sofka.questions.answerusecases.UpdateAnswerUseCase;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.AnswerToAnswerDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Clase que contiene la definicion de las rutas de la API
 * @author Sofka
 * @version 1.0
 * @since 1.0
 * @see RouterFunction
 * @see ServerResponse
 * @see Mono
 * @see BodyInserters
 * @see MediaType
 * @see Function
 * @see Parameter
 * @see ParameterIn
 * @see ApiResponse
 * @see RouterOperation
 * @see AddAnswerUseCase
 * @see AddAnswerToAnswerUseCase
 * @see UpdateAnswerUseCase
 * @see UpdateAnswerToAnswerUseCase
 * @see GetAnswerUseCase
 * @see AnswerDTO
 * @see AnswerToAnswerDTO
 */
@Configuration
public class AnswerRouter {

        /**
         * Metodo que define el entrypoint del Post de answer de la API.
         * @param addAnswerUseCase UseCase que se encarga de agregar una respuesta a una pregunta.
         * @return Respuesta de la API con un Body de tipo AnswerDTO.
         */
        @Bean
        @RouterOperation(beanClass = AddAnswerUseCase.class, beanMethod = "apply",
                operation = @Operation(operationId = "addAnswer", summary = "add answer",  tags = {"Answer"},
        responses = {
                @ApiResponse(responseCode = "200", description = "successful operation"),
                @ApiResponse(responseCode = "400", description = "Invalid Answer ID supplied"),
                @ApiResponse(responseCode = "404", description = "Answer not found") }))
        public RouterFunction<ServerResponse> addAnswer(AddAnswerUseCase addAnswerUseCase) {
                return route(POST("/add").and(accept(MediaType.APPLICATION_JSON)),
                        request -> request.bodyToMono(AnswerDTO.class)
                                .flatMap(addAnswerDTO -> addAnswerUseCase.apply(addAnswerDTO)
                                        .flatMap(result -> ServerResponse.ok()
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .bodyValue(result))
                                )
                );
        }

        /**
         * Metodo que define el entrypoint del Post de answerToAnswer de la API.
         * @param addAnswerToAnswerUseCase UseCase que se encarga de agregar una respuesta a una respuesta.
         * @return Respuesta de la API con un Body de tipo AnswerToAnswerDTO.
         */
        @Bean
        @RouterOperation(beanClass = AddAnswerToAnswerUseCase.class, beanMethod = "apply",
                operation = @Operation(operationId = "addAnswerToAnswer", summary = "add answer to answer",  tags = {"Answer"},
                        responses = {
                @ApiResponse(responseCode = "200", description = "successful operation"),
                @ApiResponse(responseCode = "400", description = "Invalid Answer ID supplied"),
                @ApiResponse(responseCode = "404", description = "Answer not found") }))
        public RouterFunction<ServerResponse> addAnswerToAnswer(AddAnswerToAnswerUseCase addAnswerToAnswerUseCase) {
                return route(POST("/addAnswer").and(accept(MediaType.APPLICATION_JSON)),
                        request -> request.bodyToMono(AnswerToAnswerDTO.class)
                                .flatMap(addAnswerDTO -> addAnswerToAnswerUseCase.apply(addAnswerDTO)
                                        .flatMap(result -> ServerResponse.ok()
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .bodyValue(result))
                                )
                );

        }

        /**
         * Metodo que define el entrypoint del Put de answer de la API.
         * @param updateAnswerUseCase UseCase que se encarga de actualizar una respuesta.
         * @return Respuesta de la API con el Id del answer actualizado.
         */
        @Bean
        @RouterOperation(beanClass = UpdateAnswerUseCase.class, beanMethod = "apply", operation = @Operation(operationId = "updateAsnwer", summary = "update answer", tags = {
                "Answer" }, responses = {
                @ApiResponse(responseCode = "200", description = "successful operation"),
                @ApiResponse(responseCode = "400", description = "Invalid parameters in body supplied"),
                @ApiResponse(responseCode = "404", description = "Answer not found") }))
        public RouterFunction<ServerResponse> updateAsnwer(UpdateAnswerUseCase updateAnswerUseCase) {
                Function<AnswerDTO, Mono<ServerResponse>> executor = AnswerDTO -> updateAnswerUseCase.apply(AnswerDTO)
                        .flatMap(result -> ServerResponse.ok()
                                .contentType(MediaType.TEXT_PLAIN)
                                .bodyValue(result));

                return route(
                        PUT("/updateAnswer").and(accept(MediaType.APPLICATION_JSON)),
                        request -> request.bodyToMono(AnswerDTO.class).flatMap(executor)
                );
        }

        /**
         * Metodo que define el entrypoint del Put de answerToAnswer de la API.
         * @param updateAnswerToAnswerUseCase UseCase que se encarga de actualizar una respuesta de una respuesta.
         * @return Respuesta de la API con el Id del answerToAnswer actualizado.
         */
        @Bean
        @RouterOperation(beanClass = UpdateAnswerToAnswerUseCase.class, beanMethod = "apply", operation = @Operation(operationId = "updateAsnwerToAnswer",
                summary = "update answer to answer", tags = {"Answer" },
                responses = {
                @ApiResponse(responseCode = "200", description = "successful operation"),
                @ApiResponse(responseCode = "400", description = "Invalid parameters in body supplied"),
                @ApiResponse(responseCode = "404", description = "Answer not found") }))
        public RouterFunction<ServerResponse> updateAsnwerToAnswer(UpdateAnswerToAnswerUseCase updateAnswerToAnswerUseCase) {
                Function<AnswerToAnswerDTO, Mono<ServerResponse>> executor = AnswerToAnswerDTO -> updateAnswerToAnswerUseCase.apply(AnswerToAnswerDTO)
                        .flatMap(result -> ServerResponse.ok()
                                .contentType(MediaType.TEXT_PLAIN)
                                .bodyValue(result));

                return route(
                        PUT("/updateAnswerToAnswer").and(accept(MediaType.APPLICATION_JSON)),
                        request -> request.bodyToMono(AnswerToAnswerDTO.class).flatMap(executor)
                );
        }

        /**
         * Metodo que define el entrypoint del Get de answer de la API.
         * @param getAnswerUseCase UseCase que se encarga de obtener una respuesta por su ID.
         * @return Respuesta de la API con un Body de tipo AnswerDTO.
         */
        @Bean
        @RouterOperation(operation = @Operation(operationId = "getAnswer", summary = "Find answer by ID", tags = {
                "Answer" }, parameters = {
                @Parameter(in = ParameterIn.PATH, name = "id", description = "Answer Id") }, responses = {
                @ApiResponse(responseCode = "200", description = "successful operation"),
                @ApiResponse(responseCode = "400", description = "Invalid Answer ID supplied"),
                @ApiResponse(responseCode = "404", description = "Answer not found") }))
        public RouterFunction<ServerResponse> getAnswer(GetAnswerUseCase getAnswerUseCase) {
                return route(
                        GET("/getAnswer/{id}").and(accept(MediaType.APPLICATION_JSON)),
                        request -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromPublisher(getAnswerUseCase.apply(
                                                request.pathVariable("id")),
                                        AnswerDTO.class)));
        }
}