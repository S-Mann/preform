/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sukhy.preform;

/**
 *
 * @author Sukhy <https://github.com/S-Mann>
 */
public class PrefORM {

    public static void main(String[] args) throws Exception {
        try (Resource resource = new Resource()) {
            resource.createResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
