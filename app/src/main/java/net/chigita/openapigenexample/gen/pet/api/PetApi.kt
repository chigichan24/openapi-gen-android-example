package net.chigita.openapigenexample.gen.pet.api

import net.chigita.openapigenexample.gen.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import net.chigita.openapigenexample.gen.pet.model.ApiResponse
import net.chigita.openapigenexample.gen.pet.model.Pet

import okhttp3.MultipartBody

interface PetApi {
    /**
     * Add a new pet to the store
     * 
     * Responses:
     *  - 405: Invalid input
     * 
     * @param body Pet object that needs to be added to the store 
     * @return [Unit]
     */
    @POST("pet")
    suspend fun addPet(@Body body: Pet): Response<Unit>

    /**
     * Deletes a pet
     * 
     * Responses:
     *  - 400: Invalid ID supplied
     *  - 404: Pet not found
     * 
     * @param petId Pet id to delete 
     * @param apiKey  (optional)
     * @return [Unit]
     */
    @DELETE("pet/{petId}")
    suspend fun deletePet(@Path("petId") petId: kotlin.Long, @Header("api_key") apiKey: kotlin.String): Response<Unit>

    /**
     * Finds Pets by status
     * Multiple status values can be provided with comma separated strings
     * Responses:
     *  - 200: successful operation
     *  - 400: Invalid status value
     * 
     * @param status Status values that need to be considered for filter 
     * @return [kotlin.collections.List<Pet>]
     */
    @GET("pet/findByStatus")
    suspend fun findPetsByStatus(@Query("status") status: kotlin.collections.List<kotlin.String>): Response<kotlin.collections.List<Pet>>

    /**
     * Finds Pets by tags
     * Muliple tags can be provided with comma separated strings. Use         tag1, tag2, tag3 for testing.
     * Responses:
     *  - 200: successful operation
     *  - 400: Invalid tag value
     * 
     * @param tags Tags to filter by 
     * @return [kotlin.collections.List<Pet>]
     */
    @Deprecated("This api was deprecated")
    @GET("pet/findByTags")
    suspend fun findPetsByTags(@Query("tags") tags: kotlin.collections.List<kotlin.String>): Response<kotlin.collections.List<Pet>>

    /**
     * Find pet by ID
     * Returns a single pet
     * Responses:
     *  - 200: successful operation
     *  - 400: Invalid ID supplied
     *  - 404: Pet not found
     * 
     * @param petId ID of pet to return 
     * @return [Pet]
     */
    @GET("pet/{petId}")
    suspend fun getPetById(@Path("petId") petId: kotlin.Long): Response<Pet>

    /**
     * Update an existing pet
     * 
     * Responses:
     *  - 400: Invalid ID supplied
     *  - 404: Pet not found
     *  - 405: Validation exception
     * 
     * @param body Pet object that needs to be added to the store 
     * @return [Unit]
     */
    @PUT("pet")
    suspend fun updatePet(@Body body: Pet): Response<Unit>

    /**
     * Updates a pet in the store with form data
     * 
     * Responses:
     *  - 405: Invalid input
     * 
     * @param petId ID of pet that needs to be updated 
     * @param name Updated name of the pet (optional)
     * @param status Updated status of the pet (optional)
     * @return [Unit]
     */
    @FormUrlEncoded
    @POST("pet/{petId}")
    suspend fun updatePetWithForm(@Path("petId") petId: kotlin.Long, @Field("name") name: kotlin.String, @Field("status") status: kotlin.String): Response<Unit>

    /**
     * uploads an image
     * 
     * Responses:
     *  - 200: successful operation
     * 
     * @param petId ID of pet to update 
     * @param additionalMetadata Additional data to pass to server (optional)
     * @param file file to upload (optional)
     * @return [ApiResponse]
     */
    @Multipart
    @POST("pet/{petId}/uploadImage")
    suspend fun uploadFile(@Path("petId") petId: kotlin.Long, @Part("additionalMetadata") additionalMetadata: kotlin.String, @Part file: MultipartBody.Part): Response<ApiResponse>

}
