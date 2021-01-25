package com.ruinscraft.minelink.storage;

import com.ruinscraft.minelink.LinkedAccount;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface MineLinkStorage {

    CompletableFuture<Void> saveLinkedAccount(LinkedAccount linkedAccount);

    CompletableFuture<List<LinkedAccount>> queryLinkedAccounts(UUID mojangId);

}
