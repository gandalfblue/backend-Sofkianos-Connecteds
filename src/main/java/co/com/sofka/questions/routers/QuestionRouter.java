package co.com.sofka.questions.routers;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.questionusecases.CreateUseCase;
import co.com.sofka.questions.questionusecases.DeleteUseCase;
import co.com.sofka.questions.questionusecases.GetUseCase;
import co.com.sofka.questions.questionusecases.ListUseCase;
import co.com.sofka.questions.questionusecases.OwnerListUseCase;
import co.com.sofka.questions.questionusecases.UpdateUseCase;
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

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Clase que se encarga de definir las rutas de la API
 *
 * @author Sofka
 * @version 1.0
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
 * @see CreateUseCase
 * @see DeleteUseCase
 * @see GetUseCase
 * @see ListUseCase
 * @see OwnerListUseCase
 * @see UpdateUseCase
 * @see QuestionDTO
 * @since 1.0
 */
@Configuration
public class QuestionRouter {

    /**
     * Metodo que define el entrypoint del GETALL de question de la API.
     * @param listUseCase UseCase que se encarga de listar las preguntas.
     * @return Respuesta de la API con un Body de tipo List<QuestionDTO>.
     */
    @Bean
    @RouterOperation(beanClass = ListUseCase.class, beanMethod = "get", operation = @Operation(operationId = "getAll", summary = "Get al question", tags = {
            "Question"}, responses = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Questions not found")}))
    public RouterFunction<ServerResponse> getAll(ListUseCase listUseCase) {
        return route(GET("/getAll"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listUseCase.get(),
                                QuestionDTO.class)));
    }

    /**
     * Metodo que define el entrypoint del GET/IDUSER de question de la API.
     * @param ownerListUseCase UseCase que se encarga de listar las preguntas de un usuario por su ID.
     * @return Respuesta de la API con un Body de tipo Lis<QuestionDTO>.
     */
    @Bean
    @RouterOperation(operation = @Operation(operationId = "getOwnerAll", summary = "Find all questions by userID", tags = {
            "Question"}, parameters = {
            @Parameter(in = ParameterIn.PATH, name = "userId", description = "User Id")}, responses = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid User ID supplied"),
            @ApiResponse(responseCode = "404", description = "Employee not found")}))
    public RouterFunction<ServerResponse> getOwnerAll(OwnerListUseCase ownerListUseCase) {
        return route(
                GET("/getOwnerAll/{userId}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                ownerListUseCase.apply(request.pathVariable("userId")),
                                QuestionDTO.class)));
    }

    /**
     * Metodo que define el entrypoint del POST de question de la API.
     * @param createUseCase UseCase que se encarga de crear una pregunta.
     * @return Respuesta de la API con el Id de la pregunta creada.
     */
    @Bean
    @RouterOperation(beanClass = CreateUseCase.class, beanMethod = "apply", operation = @Operation(operationId = "create", summary = "Create question", tags = {
            "Question"}, responses = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters in body supplied"),
            @ApiResponse(responseCode = "404", description = "Questions not found")}))
    public RouterFunction<ServerResponse> create(CreateUseCase createUseCase) {
        Function<QuestionDTO, Mono<ServerResponse>> executor = questionDTO -> createUseCase.apply(questionDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result));

        return route(
                POST("/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(QuestionDTO.class).flatMap(executor));
    }

    /**
     * Metodo que define el entrypoint del PUT de question de la API.
     *
     * @param updateUseCase UseCase que se encarga de actualizar una pregunta.
     * @return Respuesta de la API con el Id de la pregunta actualizada.
     */
    @Bean
    @RouterOperation(beanClass = UpdateUseCase.class, beanMethod = "apply", operation = @Operation(operationId = "update", summary = "Update question", tags = {
            "Question"}, responses = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters in body supplied"),
            @ApiResponse(responseCode = "404", description = "Questions not found")}))
    public RouterFunction<ServerResponse> update(UpdateUseCase updateUseCase) {
        Function<QuestionDTO, Mono<ServerResponse>> executor = QuestionDTO -> updateUseCase.apply(QuestionDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result));

        return route(
                PUT("/update").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(QuestionDTO.class).flatMap(executor)
        );
    }

    /**
     * Metodo que define el entrypoint del GET/ID de question de la API.
     * @param getUseCase UseCase que se encarga de obtener una pregunta por su ID.
     * @return Respuesta de la API con un Body de tipo QuestionDTO.
     */
    @Bean
    @RouterOperation(operation = @Operation(operationId = "QuestionDTO", summary = "Find question by ID", tags = {
            "Question"}, parameters = {
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Question Id")}, responses = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid Question ID supplied"),
            @ApiResponse(responseCode = "404", description = "Question not found")}))
    public RouterFunction<ServerResponse> get(GetUseCase getUseCase) {
        return route(
                GET("/get/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getUseCase.apply(
                                        request.pathVariable("id")),
                                QuestionDTO.class)));
    }

    /**
     * Metodo que define el entrypoint del DELETE/ID de question de la API.
     * @param deleteUseCase UseCase que se encarga de eliminar una pregunta por su ID.
     * @return  Respuesta de la API con un Mono<Void>.
     */
    @Bean
    @RouterOperation(operation = @Operation(operationId = "delete", summary = "Delete question by ID", tags = {
            "Question"}, parameters = {
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Question Id")}, responses = {
            @ApiResponse(responseCode = "202", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid Question ID supplied"),
            @ApiResponse(responseCode = "404", description = "Question not found")}))
    public RouterFunction<ServerResponse> delete(DeleteUseCase deleteUseCase) {
        return route(
                DELETE("/delete/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                deleteUseCase.apply(request.pathVariable("id")),
                                Void.class)));
    }
}