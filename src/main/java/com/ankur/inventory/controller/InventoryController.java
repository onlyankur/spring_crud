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
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    @Operation(method = "findByName", summary = "Finds an item by name",tags="post")
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

        logRequest("findByName",request);
        return inventoryService.findByName(request);
    }


    /**
     * Uses @RequestParam: 'http://localhost:6009/inventory/services/locateById?id=3'
     */
    @RequestMapping(value = "locateById", method = RequestMethod.GET, produces = "application/json")
    @Operation(method="locateById",summary = "Retrieves all items from the inventory based on the id provided", tags = "get")
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
        logRequest("locateById","No request body");
        return inventoryService.filterById(id);
    }


    /**
     * Uses @PathParam: http://localhost:6009/inventory/services/all/3
     */
    @RequestMapping(value = "all/{id}", method = RequestMethod.GET, produces = "application/json")
    @Operation(method="filterById",summary = "Retrieves all items from the inventory based on the id provided", tags = "get")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = InventoryFindByIdResponse.class), mediaType = "application/json"),
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
        logRequest("all/{id}","No request body");
        return inventoryService.filterById(id);
    }


    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @Operation(method = "delete", summary = "Deletes an item from inventory", tags = "delete")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = InventoryDeleteItemResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            })
    })
    public ResponseEntity<?> deleteWithParamVariable(@RequestHeader(value = CLIENT_ID) String clientId,
                                                     @Parameter(name = "id", required = true, example = "3") @PathVariable("id") final int id) {
        logRequest("deleteWithParamVariable","No request body");
        return inventoryService.remove(id);
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    @Operation(method = "update", summary = "Updates an existing item from the Inventory", tags = "put")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = InventoryUpdateItemResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            })
    })
    public ResponseEntity<?> putWithRequestBody(@RequestHeader(value = CLIENT_ID) String clientId,
                                                @Valid @RequestBody InventoryUpdateItemRequest request) {
        logRequest("putWithRequestBody",request);
        return inventoryService.update(request);
    }

    /**
     * Updates ItemInfo object only
     */
    @RequestMapping(value = "updateItemInfo", method = RequestMethod.PATCH, produces = "application/json", consumes = "application/json")
    @Operation(method = "updateItemInfo", summary = "Updates an existing item from the Inventory", tags = "patch")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = InventoryUpdateItemInfoResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            })
    })
    public ResponseEntity<?> patchWithRequestBody(@RequestHeader(value = CLIENT_ID) String clientId,
                                                  @Valid @RequestBody InventoryUpdateItemInfoRequest request) {
        logRequest("putWithRequestBody",request);
        return inventoryService.updateItemInfo(request);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @Operation(method = "add", summary = "Adds a new item in the Inventory", tags = "post")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = InventoryAddItemResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            })
    })
    public ResponseEntity<?> postWithRequestBody(@RequestHeader(value = CLIENT_ID) String clientId,
                                                 @Valid @RequestBody InventoryAddItemRequest request) {
        logRequest("add",request);
        return inventoryService.add(request);
    }


    @RequestMapping(value = "findById", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @Operation(method = "findById", summary = "Find a single inventory item by id", tags = "post")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = InventoryFindByIdResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            })
    })
    public ResponseEntity<?> postWithRequestBody(@RequestHeader(value = CLIENT_ID) String clientId,
                                                 @Valid @RequestBody InventoryFindByIdRequest request) {
        logRequest("findById",request);
        return inventoryService.findById(request);
    }

    @RequestMapping(value = "listall", method = {RequestMethod.GET}, produces = "application/json")
    @Operation(method = "listall", summary = "Get all inventory items", tags = "get")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = InventoryListAllResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            })
    })
    public ResponseEntity<?> getWithoutAnyRequestParamOrPathVariable(@RequestHeader(value = CLIENT_ID) String clientId
    ) {
        logRequest("listall","No request body");
        return inventoryService.listAll();
    }


    /**
     * When running in Postman, body may or may not be empty
     * Check the 'Headers' in the response. That will should not be empty.
     */
    @RequestMapping(value = "/options", method = RequestMethod.OPTIONS)
    public ResponseEntity options(HttpServletResponse response) {
        response.setHeader("Allow", "HEAD,GET,PUT,OPTIONS,POST,PATCH,DELETE");
        logRequest("options","No request body");
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "head/{id}", method = {RequestMethod.HEAD}, produces = "application/json")
    @Operation(method = "head/{id}", summary = "Show header options", tags = "head")
    @ApiResponses({
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            })
    })
    public ResponseEntity<?> head(@RequestHeader(value = CLIENT_ID) String clientId, @Parameter(name = "id", example = "2") @PathVariable("id") int id
    ) {

        final HttpHeaders responseHeaders = new HttpHeaders();
        ResponseEntity<InventoryFindByIdResponse> response = (ResponseEntity<InventoryFindByIdResponse>) inventoryService.filterById(id);
        InventoryFindByIdResponse responseBody = response.getBody();
        responseHeaders.setContentLength(responseBody.toString().length());
        responseHeaders.set("author", "onlyankur@gmail.com");
        responseHeaders.set("Access-Control-Expose-Headers", "*");
        logRequest("head","No request body");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }


    /**
     * Trace type of operation is disabled for security reasons by browsers to avoid security conditions like cross site scripting.
     * Trace is usually used for diagnostic purposes.
     */
    @RequestMapping(value = "trace", method = RequestMethod.TRACE, produces = "application/json", consumes = "application/json")
    @Operation(method = "trace", summary = "Tracing operation", tags = "trace")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = InventoryFindByIdResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            })
    })
    public ResponseEntity<?> trace(@RequestHeader(value = CLIENT_ID) String clientId,
                                     @Valid @RequestBody InventoryFindByIdRequest request) {
        logRequest("trace","No request body");
        return inventoryService.findById(request);
    }


    /**
         'http://localhost:6009/inventory/services/putWithRequestParams?id=3&Description=Classic&Manufactured%20By=IKEA&Name=Lamp&Price=99.99'
     */
    @RequestMapping(value = "putWithRequestParams", method = RequestMethod.PUT, produces = "application/json")
    @Operation(method = "putWithRequestParams", summary = "Put operation with request parameters", tags = "put")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = InventoryFindByIdResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            })
    })
    public ResponseEntity<?> putWithRequestParam(@RequestHeader(value = CLIENT_ID) String clientId,
                                                 @Parameter(name = "id", required = true, example = "3") @RequestParam final int id,
                                                 @Parameter(name = "description", required = true, example = "Classic") @RequestParam final String description,
                                                 @Parameter(name = "manufacturedBy", required = true, example = "IKEA") @RequestParam final String manufacturedBy,
                                                 @Parameter(name = "name", required = true, example = "Lamp") @RequestParam final String name,
                                                 @Parameter(name = "price", required = true, example = "99.99") @RequestParam final Float price
    ) {

        Item item = new Item(id, name, price, new Info(manufacturedBy, description));
        InventoryUpdateItemRequest request = new InventoryUpdateItemRequest(item);
        logRequest("putWithRequestParam",request);
        return inventoryService.update(request);
    }



    /**
     * http://localhost:7000/inventory/services/postWithRequestParams?id=3
     */
    @RequestMapping(value = "postWithRequestParams", method = RequestMethod.POST, produces = "application/json")
    @Operation(method = "postWithRequestParams", summary = "postWithRequestParams", tags = "post")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = InventoryFindByIdResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            })
    })
    public ResponseEntity<?> postWithRequestParam(@RequestHeader(value = CLIENT_ID) String clientId,
                                                  @Parameter(name = "id", required = true, example = "3") @RequestParam final int id
    ) {
        logRequest("postWithRequestParam","No request body");
        return inventoryService.filterById(id);
    }

    /**
     * http://localhost:7000/inventory/services/deleteWithRequestParams?id=1
     */
    @RequestMapping(value = "deleteWithRequestParams", method = RequestMethod.DELETE, produces = "application/json")
    @Operation(method = "deleteWithRequestParams", summary = "deleteWithRequestParams", tags = "delete")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = InventoryFindByIdResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = ServiceErrorResponse.class), mediaType = "application/json"),
            })
    })
    public ResponseEntity<?> deleteWithRequestParam(@RequestHeader(value = CLIENT_ID) String clientId,
                                                    @Parameter(name = "id", required = true, example = "3") @RequestParam final int id
    ) {
        logRequest("deleteWithRequestParams","No request body");
        return inventoryService.remove(id);
    }

    private void logRequest(String operation, Object request) {
        try {
            System.out.println("*******************************************************");
            String jsonRequest = objectMapper.writeValueAsString(request);
            System.out.println("operation: "+operation);
            System.out.println("reqeust = " + jsonRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

