package net.chigita.openapigenexample.gen.pet.api

import net.chigita.openapigenexample.gen.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import net.chigita.openapigenexample.gen.pet.model.Order

interface StoreApi {
    /**
     * Delete purchase order by ID
     * For valid response try integer IDs with positive integer value.         Negative or non-integer values will generate API errors
     * Responses:
     *  - 400: Invalid ID supplied
     *  - 404: Order not found
     * 
     * @param orderId ID of the order that needs to be deleted 
     * @return [Unit]
     */
    @DELETE("store/order/{orderId}")
    suspend fun deleteOrder(@Path("orderId") orderId: kotlin.Long): Response<Unit>

    /**
     * Returns pet inventories by status
     * Returns a map of status codes to quantities
     * Responses:
     *  - 200: successful operation
     * 
     * @return [kotlin.collections.Map<kotlin.String, kotlin.Int>]
     */
    @GET("store/inventory")
    suspend fun getInventory(): Response<kotlin.collections.Map<kotlin.String, kotlin.Int>>

    /**
     * Find purchase order by ID
     * For valid response try integer IDs with value &gt;&#x3D; 1 and &lt;&#x3D; 10.         Other values will generated exceptions
     * Responses:
     *  - 200: successful operation
     *  - 400: Invalid ID supplied
     *  - 404: Order not found
     * 
     * @param orderId ID of pet that needs to be fetched 
     * @return [Order]
     */
    @GET("store/order/{orderId}")
    suspend fun getOrderById(@Path("orderId") orderId: kotlin.Long): Response<Order>

    /**
     * Place an order for a pet
     * 
     * Responses:
     *  - 200: successful operation
     *  - 400: Invalid Order
     * 
     * @param body order placed for purchasing the pet 
     * @return [Order]
     */
    @POST("store/order")
    suspend fun placeOrder(@Body body: Order): Response<Order>

}
