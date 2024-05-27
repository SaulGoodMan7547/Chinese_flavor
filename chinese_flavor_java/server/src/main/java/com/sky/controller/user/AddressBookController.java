package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.result.Result;
import com.sky.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
@Api(tags = "地址管理")
public class AddressBookController {

    @Resource
    private AddressBookMapper addressBookMapper;

    private final String url = "/list";

    @Resource
    private AddressService addressService;

    /**
     * 新增地址
     */
    @PostMapping()
    public Result add(@RequestBody AddressBook addressBook){

        addressService.add(addressBook);

        return Result.success();
    }

    /**
     * 查询当前登录用户的所有地址
     */
    @GetMapping("/list")
    public Result<List<AddressBook>> list(){

        List<AddressBook> list = addressService.list();

        return Result.success(list);
    }

    /**
     * 查询当前默认地址
     */
    @GetMapping("/default")
    public Result<AddressBook> getDefault(){

        AddressBook defaultAddress = addressService.getDefaultAddress();

        return Result.success(defaultAddress);
    }

    /**
     * 根据id查询地址
     */
    @GetMapping("/{id}")
    public Result<AddressBook> getById(@PathVariable("id") Long id){

        AddressBook addressBook = addressService.getById(id);

        return Result.success(addressBook);
    }

    /**
     * 根据id修改地址
     */
    @PutMapping()
    public Result update(@RequestBody AddressBook addressBook){


        if(addressBook.getCityName().equals("到店自取") || addressBook.getCityName().equals("到店堂食")){
            return Result.error("该地址不允许修改");
        }

        addressService.update(addressBook);

        return Result.success();
    }

    /**
     * 根据id删除地址
     */
    @DeleteMapping()
    public Result deleteById(Long id){

        AddressBook address = addressBookMapper.getById(id);
        if(address.getCityName().equals("到店自取") || address.getCityName().equals("到店堂食")){
            return Result.error("该地址不允许删除");
        }

        addressService.deleteById(id);


        return Result.success();
    }

    /**
     * 设置默认地址
     *
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result setDefault(@RequestBody AddressBook addressBook) {
        log.info("id:{}",addressBook.getId());

        addressService.setDefault(addressBook.getId());

        return Result.success();
    }
}
