/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sukhy.preform;

import java.util.List;

/**
 *
 * @author sukhi
 */
public class PrefORM {

    public static void main(String[] args) throws Exception {
        try(Resource resource = new Resource()) {
            List columnDataTypes = resource.getColumnDataTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
