package com.ankur.inventory.controller;

import com.ankur.inventory.domain.Info;
import com.ankur.inventory.domain.*;
import com.ankur.inventory.service.InventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;




@RestController
@RequestMapping("/inventory/services")
@Tag(name = "Inventory Management", description = "Inventory Management API")
@CrossOrigin(allowedHeaders = "*", maxAge = 3600)
public class InventoryController {

    private static final String CLIENT_ID = "client-id";
    private InventoryService inventoryService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    @RequestMapping(value = "findByName", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @Operation(method = "findByName", summary = "post with a request body",tags="post")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = InventoryFindByNameResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            })
    })
    public ResponseEntity<?> findByName(@RequestHeader(value = CLIENT_ID) String clientId,
                                                 @Valid @RequestBody InventoryFindByNameRequest request) {

        logRequest(request);
        return inventoryService.findByName(request);
    }


    /**
     * Uses @RequestParam: 'http://localhost:6009/inventory/services/locateById?id=3'
     */
    @RequestMapping(value = "locateById", method = RequestMethod.GET, produces = "application/json")
    @Operation(method="locateById",summary = "Locate by Id using Http Params", tags = "get")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = InventoryFindByNameResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            })
    })
    public ResponseEntity<?> getWithRequestParam(@RequestHeader(value = CLIENT_ID) String clientId,
                                                 @Parameter(name = "id", required = true, example = "3") @RequestParam final int id
    ) {
        return inventoryService.filterById(id);
    }


    /**
     * Uses @PathParam: http://localhost:6009/inventory/services/all/3
     */
    @RequestMapping(value = "all/{id}", method = RequestMethod.GET, produces = "application/json")
//    @ApiOperation(value = "Filter by Id", nickname = "all/{id}", notes = "Another way to find inventory items by id")
    @Operation(method="filterById",summary = "all/{id}", tags = "get")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = InventoryFindByNameResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            })
    })
    public ResponseEntity<?> getWithPathVariable(@RequestHeader(value = CLIENT_ID) String clientId,
                                                 @Parameter(name = "id", required = true, example = "3") @PathVariable("id") final int id
    ) {
        return inventoryService.filterById(id);
    }
//
//
//    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
//    @ApiOperation(value = "delete", notes = "Removes an item from the Inventory", nickname = "delete")
//    @ApiResponses(value = {@ApiResponse(code = 400, message = "Missing / invalid parameter", response = ServiceErrorResponse.class),
//            @ApiResponse(code = 200, message = "Success", response = InventoryDeleteItemResponse.class)})
//    public ResponseEntity<?> deleteWithParamVariable(@RequestHeader(value = CLIENT_ID) String clientId,
//                                                     @ApiParam(value = "My Id", required = true, example = "3") @PathVariable("id") final int id) {
//        return inventoryService.remove(id);
//    }
//
//    @RequestMapping(value = "update", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
//    @ApiOperation(value = "update", notes = "Updates an existing item from the Inventory", nickname = "update")
//    @ApiResponses(value = {@ApiResponse(code = 400, message = "Missing / invalid parameter", response = ServiceErrorResponse.class),
//            @ApiResponse(code = 200, message = "Success", response = InventoryUpdateItemResponse.class)})
//    public ResponseEntity<?> putWithRequestBody(@RequestHeader(value = CLIENT_ID) String clientId,
//                                                @Valid @RequestBody InventoryUpdateItemRequest request) {
//        return inventoryService.update(request);
//    }
//
//    /**
//     * Updates ItemInfo object only
//     */
//    @RequestMapping(value = "updateItemInfo", method = RequestMethod.PATCH, produces = "application/json", consumes = "application/json")
//    @ApiOperation(value = "updateItemInfo", notes = "Updates an existing item from the Inventory", nickname = "update item info")
//    @ApiResponses(value = {@ApiResponse(code = 400, message = "Missing / invalid parameter", response = ServiceErrorResponse.class),
//            @ApiResponse(code = 200, message = "Success", response = InventoryUpdateItemInfoResponse.class)})
//    public ResponseEntity<?> patchWithRequestBody(@RequestHeader(value = CLIENT_ID) String clientId,
//                                                  @Valid @RequestBody InventoryUpdateItemInfoRequest request) {
//        return inventoryService.updateItemInfo(request);
//    }
//
//    @RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
//    @ApiOperation(value = "add", notes = "Adds a new item in the Inventory", nickname = "add")
//    @ApiResponses(value = {@ApiResponse(code = 400, message = "Missing / invalid parameter", response = ServiceErrorResponse.class),
//            @ApiResponse(code = 200, message = "Success", response = InventoryAddItemResponse.class)})
//    public ResponseEntity<?> postWithRequestBody(@RequestHeader(value = CLIENT_ID) String clientId,
//                                                 @Valid @RequestBody InventoryAddItemRequest request) {
//        logRequest(request);
//        return inventoryService.add(request);
//    }
//
//
//    @RequestMapping(value = "findById", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
//    @ApiOperation(value = "findById", notes = "Find a single inventory item by id", nickname = "findById")
//    @ApiResponses(value = {@ApiResponse(code = 400, message = "Missing / invalid parameter", response = ServiceErrorResponse.class),
//            @ApiResponse(code = 200, message = "Success", response = InventoryFindByIdResponse.class)})
//    public ResponseEntity<?> postWithRequestBody(@RequestHeader(value = CLIENT_ID) String clientId,
//                                                 @Valid @RequestBody InventoryFindByIdRequest request) {
//        return inventoryService.findById(request);
//    }
//
//    @RequestMapping(value = "listall", method = {RequestMethod.GET}, produces = "application/json")
//    @ApiOperation(value = "listall", notes = "Get all inventory items", nickname = "listall")
//    @ApiResponses(value = {@ApiResponse(code = 400, message = "Missing / invalid parameter", response = ServiceErrorResponse.class),
//            @ApiResponse(code = 200, message = "Success", response = InventoryListAllResponse.class)})
//    public ResponseEntity<?> getWithoutAnyRequestParamOrPathVariable(@RequestHeader(value = CLIENT_ID) String clientId
//    ) {
//        return inventoryService.listAll();
//    }
//
//
//    /**
//     * When running in Postman, body may or may not be empty
//     * Check the 'Headers' in the response. That will should not be empty.
//     */
//    @RequestMapping(value = "/options", method = RequestMethod.OPTIONS)
//    public ResponseEntity options(HttpServletResponse response) {
//        response.setHeader("Allow", "HEAD,GET,PUT,OPTIONS,POST,PATCH,DELETE");
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//
//    @RequestMapping(value = "head/{id}", method = {RequestMethod.HEAD}, produces = "application/json")
//    @ApiOperation(value = "head", notes = "Show header options", nickname = "head")
//    @ApiResponses(value = {@ApiResponse(code = 400, message = "Missing / invalid parameter", response = ServiceErrorResponse.class),
//            @ApiResponse(code = 200, message = "Success", response = String.class)})
//    public ResponseEntity<?> head(@RequestHeader(value = CLIENT_ID) String clientId, @ApiParam(value = "My Id", example = "2") @PathVariable("id") int id
//    ) {
//
//        final HttpHeaders responseHeaders = new HttpHeaders();
//        ResponseEntity<InventoryFindByIdResponse> response = (ResponseEntity<InventoryFindByIdResponse>) inventoryService.filterById(id);
//        InventoryFindByIdResponse responseBody = response.getBody();
//        responseHeaders.setContentLength(responseBody.toString().length());
//        responseHeaders.set("author", "onlyankur@gmail.com");
//        responseHeaders.set("Access-Control-Expose-Headers", "*");
//        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
//    }
//
//
//    /**
//     * TODO : Does not show up in Swagger. Why ?
//     */
//    @RequestMapping(value = "trace", method = RequestMethod.TRACE, produces = "application/json", consumes = "application/json")
//    @ApiOperation(value = "trace", notes = "Tracing...", nickname = "trace")
//    @ApiResponses(value = {@ApiResponse(code = 400, message = "Missing / invalid parameter", response = ServiceErrorResponse.class),
//            @ApiResponse(code = 200, message = "Success", response = InventoryFindByIdResponse.class)})
//    public ResponseEntity<?> mytrace(@RequestHeader(value = CLIENT_ID) String clientId,
//                                     @Valid @RequestBody InventoryFindByIdRequest request) {
//        return inventoryService.findById(request);
//    }
//
//
//    /**
//     * http://localhost:7000/inventory/services/putWithRequestParams?description=Classic%20wooden%20lamp&id=3&manufacturedBy=IKEA&name=Lamp&price=99.99
//     */
//    @RequestMapping(value = "putWithRequestParams", method = RequestMethod.PUT, produces = "application/json")
//    @ApiOperation(value = "putWithRequestParams", nickname = "putWithRequestParams", notes = "PUT with request params")
//    @ApiResponses(value = {@ApiResponse(code = 400, message = "Missing / invalid parameter", response = ServiceErrorResponse.class),
//            @ApiResponse(code = HTTP_OK, message = "Success", response = InventoryFindByIdResponse.class),
//            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid request", response = ServiceErrorResponse.class),
//            @ApiResponse(code = HTTP_NO_CONTENT, message = "No data found", response = ServiceErrorResponse.class),
//            @ApiResponse(code = HTTP_INTERNAL_ERROR, message = "Internal server error", response = ServiceErrorResponse.class)})
//    public ResponseEntity<?> putWithRequestParam(@RequestHeader(value = CLIENT_ID) String clientId,
//                                                 @ApiParam(value = "Id", required = true, example = "3") @RequestParam final int id,
//                                                 @ApiParam(value = "Description", required = true, example = "Classic wooden lamp") @RequestParam final String description,
//                                                 @ApiParam(value = "Manufactured By", required = true, example = "IKEA") @RequestParam final String manufacturedBy,
//                                                 @ApiParam(value = "Name", required = true, example = "Lamp") @RequestParam final String name,
//                                                 @ApiParam(value = "Price", required = true, example = "99.99") @RequestParam final Float price
//    ) {
//
//        Item item = new Item(id, name, price, new Info(manufacturedBy, description));
//        InventoryUpdateItemRequest request = new InventoryUpdateItemRequest(item);
//        return inventoryService.update(request);
//    }
//
//    /**
//     * http://localhost:7000/inventory/services/postWithRequestParams?id=3
//     */
//    @RequestMapping(value = "postWithRequestParams", method = RequestMethod.POST, produces = "application/json")
//    @ApiOperation(value = "postWithRequestParams", nickname = "postWithRequestParams", notes = "Finding an Item")
//    @ApiResponses(value = {@ApiResponse(code = 400, message = "Missing / invalid parameter", response = ServiceErrorResponse.class),
//            @ApiResponse(code = HTTP_OK, message = "Success", response = InventoryFindByIdResponse.class),
//            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid request", response = ServiceErrorResponse.class),
//            @ApiResponse(code = HTTP_NO_CONTENT, message = "No data found", response = ServiceErrorResponse.class),
//            @ApiResponse(code = HTTP_INTERNAL_ERROR, message = "Internal server error", response = ServiceErrorResponse.class)})
//    public ResponseEntity<?> postWithRequestParam(@RequestHeader(value = CLIENT_ID) String clientId,
//                                                  @ApiParam(value = "Id", required = true, example = "3") @RequestParam final int id
//    ) {
//        return inventoryService.filterById(id);
//    }
//
//    /**
//     * http://localhost:7000/inventory/services/deleteWithRequestParams?id=1
//     */
//    @RequestMapping(value = "deleteWithRequestParams", method = RequestMethod.DELETE, produces = "application/json")
//    @ApiOperation(value = "deleteWithRequestParams", nickname = "deleteWithRequestParams", notes = "Deleting an Item")
//    @ApiResponses(value = {@ApiResponse(code = 400, message = "Missing / invalid parameter", response = ServiceErrorResponse.class),
//            @ApiResponse(code = HTTP_OK, message = "Success", response = InventoryFindByIdResponse.class),
//            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid request", response = ServiceErrorResponse.class),
//            @ApiResponse(code = HTTP_NO_CONTENT, message = "No data found", response = ServiceErrorResponse.class),
//            @ApiResponse(code = HTTP_INTERNAL_ERROR, message = "Internal server error", response = ServiceErrorResponse.class)})
//    public ResponseEntity<?> deleteWithRequestParam(@RequestHeader(value = CLIENT_ID) String clientId,
//                                                    @ApiParam(value = "Id", required = true, example = "3") @RequestParam final int id
//    ) {
//        return inventoryService.remove(id);
//    }

    private void logRequest(Object request) {
        try {
            String jsonString = objectMapper.writeValueAsString(request);
            System.out.println("findByName reqeust = " + jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

