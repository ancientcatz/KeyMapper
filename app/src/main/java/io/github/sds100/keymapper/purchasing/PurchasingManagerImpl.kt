package io.github.sds100.keymapper.purchasing

import io.github.sds100.keymapper.base.purchasing.PurchasingError
import io.github.sds100.keymapper.base.purchasing.PurchasingManager
import io.github.sds100.keymapper.base.purchasing.RevenueCatEntitlementId
import io.github.sds100.keymapper.base.purchasing.RevenueCatState
import io.github.sds100.keymapper.common.utils.KMResult
import io.github.sds100.keymapper.common.utils.State
import io.github.sds100.keymapper.common.utils.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * The non-Play (FOSS) build cannot talk to Google Play billing, so purchases cannot grant
 * entitlements. To make every premium feature usable in this build, all entitlements are
 * reported as already granted. No purchase records, tokens, or billing responses are faked:
 * launching a purchase flow, restoring purchases, fetching prices, customer IDs and per-
 * package purchase state still return [PurchasingError.PurchasingNotImplemented]. Only the
 * entitlement query that gates feature access is short-circuited, so the existing premium
 * indicators, labels and badges are unchanged.
 */
class PurchasingManagerImpl : PurchasingManager {
    override val onCompleteProductPurchase: MutableSharedFlow<RevenueCatEntitlementId> =
        MutableSharedFlow()
    override val state: Flow<State<KMResult<RevenueCatState>>> = MutableStateFlow(
        State.Data(
            Success(
                RevenueCatState(
                    entitlements = RevenueCatEntitlementId.values().toSet(),
                    offeringMetadata = emptyMap(),
                ),
            ),
        ),
    )

    override suspend fun launchPurchasingFlow(
        packageId: String,
        verifyEntitlements: Array<RevenueCatEntitlementId>,
    ): KMResult<Unit> {
        return PurchasingError.PurchasingNotImplemented
    }

    override suspend fun isPackagePurchased(packageId: String): KMResult<Boolean> {
        return PurchasingError.PurchasingNotImplemented
    }

    override suspend fun getNonSubscriptionPurchaseCount(packageId: String): KMResult<Int> {
        return PurchasingError.PurchasingNotImplemented
    }

    override suspend fun getPackagePrice(packageId: String): KMResult<String> {
        return PurchasingError.PurchasingNotImplemented
    }

    override suspend fun hasEntitlement(entitlement: RevenueCatEntitlementId): KMResult<Boolean> {
        // All premium entitlements are unlocked by default in the non-Play (FOSS) build.
        return Success(true)
    }

    override suspend fun getCurrentOfferingId(): KMResult<String?> {
        return PurchasingError.PurchasingNotImplemented
    }

    override suspend fun restorePurchases(): KMResult<Set<RevenueCatEntitlementId>> {
        return PurchasingError.PurchasingNotImplemented
    }

    override suspend fun getCustomerId(): KMResult<String> {
        return PurchasingError.PurchasingNotImplemented
    }

    override fun refresh() {}

    override fun trackCustomPaywallImpression(paywallIdentifier: String) {
        // Purchasing is not available in FOSS.
    }
}
